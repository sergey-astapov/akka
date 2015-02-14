package com.t360.numberenc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Entry {
    public final Encoded encoded;
    public Optional<List<Entry>> children;

    Entry(Encoded encoded) {
        this.encoded = encoded;
        children = Optional.empty();
    }

    public Stream<String> traverse() {
        if (!children.isPresent() || children.get().isEmpty()) {
            return Stream.of(encoded.word.get());
        }

        return children.get().stream()
                .flatMap((c) -> c.traverse()
                        .map((s) -> encoded.word.map((w) -> w + " " + s).orElse(s)));
    }
}
