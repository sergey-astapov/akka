package com.t360.numberenc;

import java.util.Optional;

public class Pair {
    public final Optional<String> word;
    public final Integer next;

    public Pair(Integer next) {
        this.word = Optional.empty();
        this.next = next;
    }

    public Pair(String word, Integer next) {
        this.word = Optional.of(word);
        this.next = next;
    }
}
