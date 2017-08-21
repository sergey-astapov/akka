package io.drawing.console.model;

public class IllegalModelStateException extends RuntimeException {
    public IllegalModelStateException(String description) {
        super("Illegal model state - " + description);
    }
}
