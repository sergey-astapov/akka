package io.drawing.console;

import io.drawing.console.controller.impl.ConsoleDrawingController;
import io.drawing.console.model.impl.MemoryDrawingModel;
import io.drawing.console.view.DrawingView;
import io.drawing.console.view.impl.ConsoleDrawingView;

public class App {
    public static void main(String[] args) {
        DrawingView v = new ConsoleDrawingView();
        MemoryDrawingModel m = new MemoryDrawingModel(v);
        ConsoleDrawingController c = new ConsoleDrawingController(m);
        c.userLoop();
    }
}
