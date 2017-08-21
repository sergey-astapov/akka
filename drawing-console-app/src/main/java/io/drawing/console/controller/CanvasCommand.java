package io.drawing.console.controller;

import io.drawing.console.model.Canvas;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class CanvasCommand implements Command {
    @NonNull
    Canvas canvas;

    public static CanvasCommand from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 3 || !Canvas.CANVAS.equals(arr[0])) {
            log.debug("Not a canvas command, input: " + input);
            return null;
        }
        try {
            Canvas figure = Canvas.builder()
                    .width(Integer.parseInt(arr[1]))
                    .height(Integer.parseInt(arr[2]))
                    .build();
            return new CanvasCommand(figure);
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
