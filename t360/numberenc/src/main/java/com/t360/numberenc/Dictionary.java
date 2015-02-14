package com.t360.numberenc;

import java.util.*;
import static java.util.logging.Level.WARNING;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.t360.numberenc.Mapping.ignore;

/**
 * Implements dictionary.
 */
public class Dictionary {
    private static final Logger LOG = Logger.getLogger(Dictionary.class.getName());

    public static final String WORD_REGEX = "[a-zA-Z-\"]+";
    public static final int WORD_MAX_LENGTH = 50;
    public static final int MAX_SIZE = 75000;

    private final String[] dictionary;

    public Dictionary(List<String> words) {
        this(words.stream());
    }

    public Dictionary(Stream<String> stream) {
        List<String> col = stream.filter((w) -> {
            boolean supported = w.length() <= WORD_MAX_LENGTH && w.matches(WORD_REGEX) &&
                    !w.startsWith("-") && !w.startsWith("\"");
            if (!supported) {
                LOG.log(WARNING, "Unsupported word: " + w);
            }
            return supported;
        }).collect(Collectors.toList());
        if (col.size() > MAX_SIZE) {
            String m = "Dictionary size is too big, current: " + col.size() + ", need: " + MAX_SIZE;
            LOG.log(Level.SEVERE, m);
            throw new RuntimeException(m);
        }
        //Collections.sort(col, Mapping::compareFirstChar);
        dictionary = col.toArray(new String[col.size()]);
    }

    public Stream<String> collect(String number) {
        return collectEncodedEntry(new Entry(new Encoded(0)), number.toCharArray()).traverse();
    }

    Entry collectEncodedEntry(Entry parent, char number[]) {
        final Encoded encoded = parent.encoded;
        final boolean isEncoded = !encoded.isDigit();
        int start = encoded.next;
        while (start < number.length && ignore(number[start])) {
            start++;
        }
        if (start == number.length) {
            return parent;
        }
        if (start > number.length) {
            throw new IllegalArgumentException("Unsupported index, current: " + start + ", need: " + number.length);
        }

        final int digitIndex = start;
        List<Integer> indexes = Collections.emptyList();
        while (start < number.length) {
            if (!ignore(number[start])) {
                indexes = SearchUtils.digitAll(dictionary, number[start]);
                if (!indexes.isEmpty() || isEncoded) {
                    break;
                }
            }
            start++;
        }

        if (indexes.isEmpty()) {
            return !isEncoded ? parent : collectNotEncodedEntry(parent, number, digitIndex);
        }

        final int begin = start;
        List<Entry> children = indexes.stream()
                .map((i) -> SearchUtils.match(dictionary[i], number, begin))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (children.isEmpty() && isEncoded) {
            return collectNotEncodedEntry(parent, number, digitIndex);
        }

        children.forEach((child) -> collectEncodedEntry(child, number));
        parent.children = Optional.of(children);
        return parent;
    }

    private Entry collectNotEncodedEntry(Entry parent, char[] number, int i) {
        Entry entry = new Entry(new Encoded(String.valueOf(number[i]), i + 1));
        collectEncodedEntry(entry, number);
        parent.children = Optional.of(Arrays.asList(entry));
        return parent;
    }
}
