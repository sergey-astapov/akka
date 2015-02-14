package com.t360.numberenc;

import java.util.Optional;

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
}
