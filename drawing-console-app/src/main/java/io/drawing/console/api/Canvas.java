package io.drawing.console.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Canvas implements Command {
    public static final String CANVAS = "C";

    @NonNull
    Integer width;
    @NonNull
    Integer height;

    @Builder
    public static Canvas from(Integer width, Integer height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Only positive numbers supported, width=" + width + ", height=" + height);
        }
        return new Canvas(width, height);
    }

    public static Canvas from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 3 || !Canvas.CANVAS.equals(arr[0])) {
            log.debug("Not a canvas command, input: " + input);
            return null;
        }
        try {
            return Canvas.builder()
                    .width(Integer.parseInt(arr[1]))
                    .height(Integer.parseInt(arr[2]))
                    .build();
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
