package io.drawing.console.api;

public class IllegalCommandFormatException extends RuntimeException {
    public IllegalCommandFormatException(String input) {
        super("Illegal command format - " + input);
    }
}
