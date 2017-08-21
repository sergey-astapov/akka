package io.drawing.console.controller;

import io.drawing.console.model.Line;
import io.drawing.console.model.Point;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class LineCommand implements Command {
    @NonNull
    Line line;

    public static LineCommand from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 5 || !Line.LINE.equals(arr[0])) {
            log.debug("Not a line command, input: " + input);
            return null;
        }
        try {
            Line line = Line.builder()
                    .start(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .end(new Point(Integer.parseInt(arr[3]), Integer.parseInt(arr[4])))
                    .build();
            return new LineCommand(line);
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
