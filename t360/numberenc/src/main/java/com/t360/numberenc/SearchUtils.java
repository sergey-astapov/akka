package com.t360.numberenc;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Search utilities.
 */
public class SearchUtils {
    public static List<Integer> digitAll(final String[] dictionary, char digit) {
        return Mapping.letters(digit).chars().boxed()
                .map((c) -> letterAll(dictionary, Character.toString((char) c.intValue())))
                .filter((l) -> !l.isEmpty())
                .flatMap((l) -> l.stream())
                .collect(Collectors.toList());
    }

    public static List<Integer> letterAll(String[] dictionary, String letter) {
        List<Integer> indexes = new LinkedList<>();
        int index = letterAny(dictionary, letter);
        if (index < 0) {
            return indexes;
        }
        indexes.add(index);
        int i = index - 1;
        while (i >= 0 && Mapping.compareFirstChar(dictionary[i], letter) == 0) {
            indexes.add(0, i--);
        }
        i = index + 1;
        while (i < dictionary.length && Mapping.compareFirstChar(dictionary[i], letter) == 0) {
            indexes.add(i++);
        }
        return indexes;
    }

    public static int letterAny(String[] dictionary, String letter) {
        return Collections.binarySearch(Arrays.asList(dictionary), letter, Mapping::compareFirstChar);
    }

    public static Optional<Entry> match(String word, char[] number, final int start) {
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
            result = Optional.of(new Entry(new Encoded(word, i)));
        }
        return result;
    }
}
