package io.drawing.console.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Rectangle implements Figure {
    public static final String RECTANGLE = "R";

    @NonNull
    Point upperLeftCorner;
    @NonNull
    Point lowerRightCorner;

    @Builder
    public static Rectangle from(Point upperLeftCorner, Point lowerRightCorner) {
        return new Rectangle(upperLeftCorner, lowerRightCorner);
    }

    @Override
    public boolean isInCanvas(Canvas c) {
        return upperLeftCorner.isInCanvas(c) && lowerRightCorner.isInCanvas(c);
    }
}
