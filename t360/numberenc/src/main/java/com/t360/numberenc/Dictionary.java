package com.t360.numberenc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.t360.numberenc.Mapping.ignore;

/**
 * Implements dictionary.
 */
public class Dictionary {
    private static final Logger LOG = LoggerFactory.getLogger(Dictionary.class);

    public static final String WORD_REGEX = "[a-zA-Z-\"]+";
    public static final int WORD_MAX_LENGTH = 50;
//    public static final int MAX_SIZE = 75000;

    private final String[] dictionary;

    public Dictionary(List<String> words) {
        this(words.stream());
    }

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
        Collections.sort(dict, Mapping::compareFirstChar);
        dictionary = dict.toArray(new String[dict.size()]);
    }

    public List<String> collect(String number) {
        return collectEntry(new Entry(new Pair(0)), number.toCharArray())
                .traverse().stream()
                .collect(Collectors.toList());
    }

    Entry collectEntry(Entry entry, char number[]) {
        int start = entry.value.next;
        while (start < number.length && ignore(number[start])) {
            start++;
        }
        if (start == number.length) {
            return entry;
        }
        if (start > number.length) {
            throw new IllegalArgumentException("Unsupported index, current: " + start + ", need: " + number.length);
        }

        List<Integer> indexes = Collections.emptyList();
        while (start < number.length) {
            if (!ignore(number[start])) {
                indexes = SearchUtils.digitAll(dictionary, number[start]);
                if (!indexes.isEmpty()) {
                    break;
                }
            }
            start++;
        }
        if (indexes.isEmpty()) {
            return entry;
        }

        final int begin = start;
        List<Entry> children = indexes.stream()
                .map((i) -> SearchUtils.match(dictionary[i], number, begin))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        children.forEach((child) -> collectEntry(child, number));
        entry.children = Optional.of(children);
        return entry;
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
