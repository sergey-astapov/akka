package io.drawing.console.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Rectangle implements Figure, Command {
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
    public boolean fitCanvas(Canvas c) {
        return upperLeftCorner.fitCanvas(c) && lowerRightCorner.fitCanvas(c);
    }

    public static Rectangle from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 5 || !Rectangle.RECTANGLE.equals(arr[0])) {
            log.debug("Not a rectangle command, input: " + input);
            return null;
        }
        try {
            return Rectangle.builder().upperLeftCorner(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .lowerRightCorner(new Point(Integer.parseInt(arr[3]), Integer.parseInt(arr[4])))
                    .build();
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
