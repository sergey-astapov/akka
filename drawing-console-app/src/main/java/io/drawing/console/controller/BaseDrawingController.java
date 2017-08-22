package io.drawing.console.controller;

import io.drawing.console.api.*;
import io.drawing.console.model.DrawingModel;

public abstract class BaseDrawingController {
    private final DrawingModel model;

    public BaseDrawingController(DrawingModel model) {
        this.model = model;
    }

    public abstract void userLoop();

    public boolean process(String input) {
        Command cmd;
        if (Quit.from(input) != null) {
            return false;
        } else if ((cmd = Canvas.from(input)) != null) {
            model.add((Canvas) cmd);
        } else if ((cmd = Line.from(input)) != null) {
            model.add((Line) cmd);
        } else if ((cmd = Rectangle.from(input)) != null) {
            model.add((Rectangle) cmd);
        } else if ((cmd = Bucket.from(input)) != null) {
            model.fill((Bucket) cmd);
        } else {
            throw new IllegalCommandFormatException(input);
        }
        return true;
    }
}
