package io.drawing.console.controller;

import io.drawing.console.model.DrawingModel;

public class DrawingController {
    private final DrawingModel model;

    public DrawingController(DrawingModel model) {
        this.model = model;
    }

    public void start() {}

    public boolean process(String input) {
        Command cmd;
        if (QuitCommand.from(input) != null) {
            return false;
        } else if ((cmd = CanvasCommand.from(input)) != null) {
            CanvasCommand command = (CanvasCommand) cmd;
            model.addCanvas(command.getCanvas());
        } else if ((cmd = LineCommand.from(input)) != null) {
            LineCommand command = (LineCommand) cmd;
            model.addFigure(command.getLine());
        } else if ((cmd = RectangleCommand.from(input)) != null) {
            RectangleCommand command = (RectangleCommand) cmd;
            model.addFigure(command.getRectangle());
        } else if ((cmd = BucketFillCommand.from(input)) != null) {
            BucketFillCommand command = (BucketFillCommand) cmd;
            model.fillBucket(command.getBucket());
        } else {
            throw new IllegalCommandFormatException(input);
        }
        return true;
    }
}
