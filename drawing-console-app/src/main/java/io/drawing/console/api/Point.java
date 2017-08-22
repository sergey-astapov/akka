package io.drawing.console.api;

import lombok.Value;

@Value
public class Point implements Canvasable {
    int x;
    int y;

    @Override
    public boolean fitCanvas(int width, int height) {
        return x > 0 && y > 0 && x <= width && y <= height;
    }
}
