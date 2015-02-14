package com.t360.numberenc;

import static java.lang.Character.toLowerCase;

/**
 * Maps digits to letters by next rules:
 *
 * E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
 * e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
 * 0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
 */
public class Mapping {
    private static String[] letters = new String[] {
            //0|   1  |   2  |   3  |  4  |  5  |   6  |   7  |   8  |   9
            "e", "jnq", "rwx", "dsy", "ft", "am", "civ", "bku", "lop", "ghz"
    };

    public static String letters(Character n) {
        int value = Character.getNumericValue(n);
        if (value < 0) throw new IllegalArgumentException("Unsupported numeric value: " + n);
        return letters[value];
    }

    public static int digit(Character w) {
        int i = 0;
        for (String l : letters) {
            if (l.contains(String.valueOf(toLowerCase(w)))) return i;
            i++;
        }
        return -1;
    }

    public static boolean exist(Character n, Character w) {
        return letters[Character.getNumericValue(n)].contains(String.valueOf(toLowerCase(w)));
    }

    public static boolean ignore(char c) {
        return c == '/' || c == '-';
    }

    public static int compareFirstChar(String w, String d) {
        String wordChar = Character.toString(w.charAt(0));
        return wordChar.toLowerCase().compareTo(d.toLowerCase());
    }
}
