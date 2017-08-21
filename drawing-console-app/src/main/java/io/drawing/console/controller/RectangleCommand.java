package io.drawing.console.controller;

import io.drawing.console.model.Point;
import io.drawing.console.model.Rectangle;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class RectangleCommand implements Command {
    @NonNull
    Rectangle rectangle;

    public static RectangleCommand from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 5 || !Rectangle.RECTANGLE.equals(arr[0])) {
            log.debug("Not a rectangle command, input: " + input);
            return null;
        }
        try {
            Rectangle rectangle = Rectangle.builder().upperLeftCorner(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .lowerRightCorner(new Point(Integer.parseInt(arr[3]), Integer.parseInt(arr[4])))
                    .build();
            return new RectangleCommand(rectangle);
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
