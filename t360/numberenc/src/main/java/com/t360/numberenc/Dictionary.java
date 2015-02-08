package com.t360.numberenc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Implements dictionary.
 */
public class Dictionary {
    private static final Logger LOG = LoggerFactory.getLogger(Dictionary.class);

    public static final String WORD_REGEX = "[a-zA-Z-\"]+";
    public static final int WORD_MAX_LENGTH = 50;

    private final ArrayList<List<String>> data;

    public Dictionary(Stream<String> stream) {
        data = new ArrayList<>(Mapping.length());
        for (int i = 0; i < Mapping.length(); i++) {
            data.add(new LinkedList<>());
        }
        stream.filter((w) -> {
            boolean supported = w.length() <= WORD_MAX_LENGTH && w.matches(WORD_REGEX) &&
                    !w.startsWith("-") && !w.startsWith("\"");
            if (!supported) {
                LOG.warn("Unsupported word: {}", w);
            }
            return supported;
        }).forEach((w) -> data.get(Mapping.numericValue(w.charAt(0))).add(w));
    }

    public void collect(List<StringBuilder> accumulators, char number[], int start) {
        StringBuilder acc = accumulators.get(accumulators.size() - 1);
        while (start < number.length) {
            if (number[start] == '/' || number[start] == '-') {
                start++;
                continue;
            }

            boolean matched = false;
            List<String> words = data.get(Character.getNumericValue(number[start]));
            for (String word : words) {
                matched = true;
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
                boolean used = isWordUsed(accumulators, word);
                if (matched && !used) {
                    LOG.debug("Word matched, number: {}, word: {}", number, word);
                    if (acc.length() > 0) acc.append(" ");
                    acc.append(word);
                    start = i;
                    break;
                }
            }
            if (matched) {
                continue;
            }
            if (!isPreviousDigit(acc)) {
                LOG.debug("No words started from: {}", number[start]);
                if (acc.length() > 0) acc.append(" ");
                acc.append(number[start]);
            }
            start++;
        }
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
