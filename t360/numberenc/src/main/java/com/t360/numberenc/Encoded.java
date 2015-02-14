package com.t360.numberenc;

import java.util.Optional;

/**
 * Holds encoded value.
 */
public class Encoded {
    public final Optional<String> word;
    public final Integer next;

    public Encoded(Integer next) {
        this.word = Optional.empty();
        this.next = next;
    }

    public Encoded(String word, Integer next) {
        this.word = Optional.of(word);
        this.next = next;
    }

    public boolean isDigit() {
        return word.map(Encoded::isDigit).orElse(false);
    }

    public static boolean isDigit(String s) {
        return !s.isEmpty() && Character.isDigit(s.charAt(s.length() - 1));
    }
}
