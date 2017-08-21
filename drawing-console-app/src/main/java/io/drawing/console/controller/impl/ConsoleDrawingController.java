package io.drawing.console.controller.impl;

import io.drawing.console.controller.DrawingController;
import io.drawing.console.controller.IllegalCommandFormatException;
import io.drawing.console.model.DrawingModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ConsoleDrawingController extends DrawingController {

    public ConsoleDrawingController(DrawingModel model) {
        super(model);
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("enter command : ");
            while (true) {
                String input = scanner.nextLine();
                try {
                    if (!process(input)) {
                        break;
                    }
                } catch (IllegalCommandFormatException e) {
                    log.error(e.getMessage(), e);
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
