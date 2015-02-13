package com.t360.numberenc;

import static java.lang.Character.toLowerCase;

/**
 * Implements mapping from letters to digits:
 * E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
 * e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
 * 0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
 */
public class Mapping {
    private static String[] letters = new String[] {
            //0|   1  |   2  |   3  |  4  |  5  |   6  |   7  |   8  |   9
            "Ee", "JNQjnq", "RWXrwx", "DSYdsy", "FTft", "AMam", "CIVciv", "BKUbku", "LOPlop", "GHZghz"
    };
    public static int length() {
        return letters.length;
    }
    public static int numericValue(Character w) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].contains(String.valueOf(toLowerCase(w)))) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unsupported character: " + w);
    }
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
}
