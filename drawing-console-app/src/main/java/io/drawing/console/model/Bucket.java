package io.drawing.console.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class Bucket implements InCanvas {
    public static final String BUCKET = "B";

    @NonNull
    Point point;
    @NonNull
    Color color;

    @Override
    public boolean isInCanvas(Canvas c) {
        return point.isInCanvas(c);
    }

    @Builder
    public static Bucket from(Point point, Color color) {
        return new Bucket(point, color);
    }
}
