package io.drawing.console.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Color {
    @NonNull
    Character value;

    public static Color from(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Color(s.charAt(0));
    }
}
