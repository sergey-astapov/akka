package io.drawing.console.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public abstract class Line implements Figure {
    public static final String LINE = "L";

    @NonNull
    Point start;
    @NonNull
    Point end;

    @Override
    public boolean isInCanvas(Canvas c) {
        return start.isInCanvas(c) && end.isInCanvas(c);
    }

    private static boolean isHorizontal(Point start, Point end) {
        return start.getY() == end.getY();
    }

    private static boolean isVertical(Point start, Point end) {
        return start.getX() == end.getX();
    }

    @Builder
    public static Line from(Point start, Point end) {
        if (isHorizontal(start, end)) {
            return new HorizontalLine(start, end);
        } else if (isVertical(start, end)) {
            return new VerticalLine(start, end);
        }
        throw new IllegalArgumentException("Can'tcreate line from start: " + start + ", end: " + end);
    }
}
