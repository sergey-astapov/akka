package io.drawing.console.view.impl;

import io.drawing.console.model.Bucket;
import io.drawing.console.model.Canvas;
import io.drawing.console.model.Figure;
import io.drawing.console.model.ModelChangedEvent;
import io.drawing.console.view.DrawingView;

public class ConsoleDrawingView implements DrawingView {
    @Override
    public void update(ModelChangedEvent event) {
        Character[][] chars = from(event);
        for (Character[] aChar : chars) {
            for (Character anAChar : aChar) {
                System.out.print(anAChar);
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("enter command : ");
    }

    private Character[][] from(ModelChangedEvent event) {
        Canvas canvas = event.getCanvas();
        DrawingTemplate template = new DrawingTemplate(canvas);

        for (Figure f : event.getFigures()) {
            template.drawFigure(f);
        }

        for (Bucket b : event.getBuckets()) {
            template.drawBucket(b);
        }

        return template.getChars();
    }
}

