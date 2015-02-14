package com.t360.numberenc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Entry {
    public final Pair value;
    public Optional<List<Entry>> children;

    Entry(Pair value) {
        this.value = value;
        children = Optional.empty();
    }

    public List<String> traverse() {
        if (!children.isPresent() || children.get().isEmpty()) {
            return Arrays.asList(value.word.get());
        }
        List<String> l = new LinkedList<>();
        for (Entry c : children.get()) {
            for (String s : c.traverse()) {
                l.add(value.word.map((w) -> w + " " + s).orElse(s));
            }
        }
        return l;
    }
}
