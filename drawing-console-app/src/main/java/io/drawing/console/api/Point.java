package io.drawing.console.api;

import lombok.Value;

@Value
public class Point implements Canvasable {
    int x;
    int y;

    public Point(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Only positive numbers supported, x=" + x + ", y=" + y);
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean fitCanvas(int width, int height) {
        return x > 0 && y > 0 && x <= width && y <= height;
    }
}
