package io.drawing.console.controller;

public class QuitCommand implements Command {
    public static QuitCommand from(String s) {
        return "Q".equals(s) ? new QuitCommand() : null;
    }
}
