package com.t360.numberenc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Contains encoded values.
 */
public class Entry {
    public final Optional<String> word;
    public final Integer next;
    public Optional<List<Entry>> children;

    public Entry(Integer next) {
        this.word = Optional.empty();
        this.next = next;
        children = Optional.empty();
    }

    public Entry(String word, Integer next) {
        this.word = Optional.of(word);
        this.next = next;
        children = Optional.empty();
    }

    public Stream<String> traverse() {
        if (!children.isPresent() || children.get().isEmpty()) {
            return Stream.of(word.get());
        }

        return children.get().stream()
                .flatMap((c) -> c.traverse()
                        .map((s) -> word.map((w) -> w + " " + s).orElse(s)));
    }

    public boolean isDigit() {
        return word.map(Entry::isDigit).orElse(false);
    }

    public static boolean isDigit(String s) {
        return !s.isEmpty() && Character.isDigit(s.charAt(s.length() - 1));
    }
}
