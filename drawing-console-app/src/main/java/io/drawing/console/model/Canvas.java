package io.drawing.console.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Canvas {
    public static final String CANVAS = "C";

    @NonNull
    Integer width;
    @NonNull
    Integer height;

    @Builder
    public static Canvas from(Integer width, Integer height) {
        return new Canvas(width, height);
    }
}
