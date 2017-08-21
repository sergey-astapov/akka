package io.drawing.console.model;

import lombok.NonNull;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class Color {
    private static final Set<Character> unsupported;
    static {
        unsupported = new HashSet<>();
        unsupported.add('|');
        unsupported.add('-');
        unsupported.add('x');
    }

    @NonNull
    Character value;

    public static Color from(String s) {
        char value = s.charAt(0);
        if (unsupported.contains(value)) {
            throw new IllegalArgumentException("This color is reserved, color: '" + value + "'");
        }
        return new Color(value);
    }
}
