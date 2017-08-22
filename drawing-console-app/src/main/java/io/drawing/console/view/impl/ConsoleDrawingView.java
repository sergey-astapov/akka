package io.drawing.console.view.impl;

import io.drawing.console.api.ModelChangedEvent;
import io.drawing.console.view.DrawingView;

public class ConsoleDrawingView implements DrawingView {
    @Override
    public void update(ModelChangedEvent event) {
        for (Character[] aChar : event.getChars()) {
            for (Character anAChar : aChar) {
                System.out.print(anAChar);
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("enter command : ");
    }
}

