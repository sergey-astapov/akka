package com.t360.numberenc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implements dictionary.
 */
public class Dictionary {
    private static final Logger LOG = LoggerFactory.getLogger(Dictionary.class);

    public static final String WORD_REGEX = "[a-zA-Z-\"]+";
    public static final int WORD_MAX_LENGTH = 50;
    public static final int MAX_SIZE = 75000;

    private final String[] dictionary;

    public Dictionary(Stream<String> stream) {
        List<String> dict = new LinkedList<>();
        stream.filter((w) -> {
            boolean supported = w.length() <= WORD_MAX_LENGTH && w.matches(WORD_REGEX) &&
                    !w.startsWith("-") && !w.startsWith("\"");
            if (!supported) {
                LOG.warn("Unsupported word: {}", w);
            }
            return supported;
        }).forEach(dict::add);
        dictionary = dict.toArray(new String[dict.size()]);
    }

    public void collect(Entry entry, char number[]) {
        int start = entry.value.start;
        while (start < number.length && (number[start] == '/' || number[start] == '-')) {
            start++;
        }
        if (start == number.length) {
            return;
        }
        if (start > number.length) {
            throw new IllegalArgumentException("Unsupported index, current: " + start + ", need: " + number.length);
        }

        List<Integer> indexes = Collections.emptyList();
        while (start < number.length) {
            indexes = search(dictionary, number[start]);
            if (!indexes.isEmpty()) {
                break;
            }
            start++;
        }
        if (indexes.isEmpty() || start == number.length) {
            return;
        }

        final int begin = start;
        List<Entry> children = indexes.stream()
                .map((i) -> match(dictionary[i], number, begin))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        children.forEach((child) -> collect(child, number));
        entry.children = Optional.of(children);
    }

    public static List<Integer> search(final String[] dictionary, char nc) {
        return Mapping.letters(nc).chars().boxed()
                .map((c) -> searchLetter(dictionary, Character.toString((char) c.intValue())))
                .filter((l) -> !l.isEmpty())
                .flatMap((l) -> l.stream())
                .collect(Collectors.toList());
    }

    public static List<Integer> searchLetter(String[] dictionary, String letter) {
        List<Integer> indexes = new LinkedList<>();
        int index = searchIndex(dictionary, letter);
        if (index >= 0) {
            indexes.add(index);
            int i = index - 1;
            while (i >= 0 && compareFirstChar(dictionary[i], letter) == 0) {
                indexes.add(0, i--);
            }
            i = index + 1;
            while (i < dictionary.length && compareFirstChar(dictionary[i], letter) == 0) {
                indexes.add(i++);
            }
        }
        return indexes;
    }

    public static int searchIndex(String[] dictionary, String d) {
        return Collections.binarySearch(Arrays.asList(dictionary), d, Dictionary::compareFirstChar);
    }

    public static int compareFirstChar(String w, String d) {
        String wordChar = Character.toString(w.charAt(0));
        return wordChar.toLowerCase().compareTo(d.toLowerCase());
    }

    public static Optional<Entry> match(String word, char[] number, int start) {
        boolean matched = true;
        int i = start;
        for (char c : word.toLowerCase().replaceAll("-", "").replaceAll("\"", "").toCharArray()) {
            if (i == number.length) {
                matched = false;
                break;
            }
            while (i < number.length && (number[i] == '-' || number[i] == '/')) {
                i++;
            }
            if (i == number.length) {
                matched = false;
                break;
            }
            if (!Mapping.exist(number[i++], c)) {
                matched = false;
                break;
            }
        }
        Optional<Entry> result = Optional.empty();
        if (matched) {
            LOG.debug("Word matched, number: {}, word: {}", number, word);
            result = Optional.of(new Entry(new Pair(word, i)));
        }
        return result;
    }

    static boolean isWordUsed(List<StringBuilder> accumulators, String word) {
        return accumulators.stream().anyMatch((sb) -> sb.indexOf(word) != -1);
    }

    static boolean isPreviousDigit(StringBuilder sb) {
        if (sb.length() == 0) return false;
        char c = sb.charAt(sb.length() - 1);
        if (c == ' ') {
            if (sb.length() == 1) throw new IllegalArgumentException("Accumulator length = 1");
            c = sb.charAt(sb.length() - 2);
            if (c == ' ') throw new IllegalArgumentException("Two spaces are unsupported");
        }
        return Character.getNumericValue(c) >= 0;
    }
}
