package io.drawing.console.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
@NonFinal
public abstract class Line implements Figure, Command {
    public static final String LINE = "L";

    @NonNull
    Point start;
    @NonNull
    Point end;

    @Override
    public boolean fitCanvas(Canvas c) {
        return start.fitCanvas(c) && end.fitCanvas(c);
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

    public static Line from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 5 || !Line.LINE.equals(arr[0])) {
            log.debug("Not a line command, input: " + input);
            return null;
        }
        try {
            return Line.builder()
                    .start(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .end(new Point(Integer.parseInt(arr[3]), Integer.parseInt(arr[4])))
                    .build();
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
