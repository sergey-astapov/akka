package io.drawing.console.api;

public class Quit implements Command {
    public static Quit from(String s) {
        return "Q".equals(s) ? new Quit() : null;
    }
}
