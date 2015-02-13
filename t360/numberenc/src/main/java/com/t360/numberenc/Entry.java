package com.t360.numberenc;

import java.util.List;
import java.util.Optional;

public class Entry {
    public final Pair value;
    public Optional<List<Entry>> children;

    Entry(Pair value) {
        this.value = value;
        children = Optional.empty();
    }
}
