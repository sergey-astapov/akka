package io.drawing.console.controller.impl;

import io.drawing.console.controller.BaseDrawingController;
import io.drawing.console.model.DrawingModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ConsoleDrawingController extends BaseDrawingController {

    public ConsoleDrawingController(DrawingModel model) {
        super(model);
    }

    @Override
    public void userLoop() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("enter command : ");
            while (true) {
                String input = scanner.nextLine();
                try {
                    if (!process(input)) {
                        break;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
