package io.drawing.console.controller;

public class IllegalCommandFormatException extends RuntimeException {
    public IllegalCommandFormatException(String input) {
        super("Illegal command format - " + input);
    }
}
