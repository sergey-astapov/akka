package com.t360.numberenc;

import java.util.Optional;

public class Pair {
    public final Optional<String> word;
    public final Integer start;

    public Pair(Integer start) {
        this.word = Optional.empty();
        this.start = start;
    }

    public Pair(String word, Integer start) {
        this.word = Optional.of(word);
        this.start = start;
    }
}
