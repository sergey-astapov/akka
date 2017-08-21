package io.drawing.console.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Point {
    int x;
    int y;

    public boolean isInCanvas(Canvas c) {
        return x > 0 && y > 0 && x <= c.getWidth() && y <= c.getHeight();
    }
}
