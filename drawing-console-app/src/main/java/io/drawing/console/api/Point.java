package io.drawing.console.api;

import lombok.Value;

@Value
public class Point implements Canvasable {
    int x;
    int y;

    @Override
    public boolean fitCanvas(Canvas c) {
        return x > 0 && y > 0 && x <= c.getWidth() && y <= c.getHeight();
    }
}
