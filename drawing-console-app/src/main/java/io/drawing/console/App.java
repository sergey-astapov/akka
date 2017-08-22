package io.drawing.console;

import io.drawing.console.controller.impl.ConsoleDrawingController;
import io.drawing.console.model.impl.MemoryDrawingModel;
import io.drawing.console.view.DrawingView;
import io.drawing.console.view.impl.ConsoleDrawingView;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class App {
    public static void main(String[] args) {
        clearConsole();

        DrawingView v = new ConsoleDrawingView();
        MemoryDrawingModel m = new MemoryDrawingModel(v);
        ConsoleDrawingController c = new ConsoleDrawingController(m);
        c.userLoop();
    }

    private static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            log.debug("Can't clean console: " + e.getMessage());
        }
    }
}
