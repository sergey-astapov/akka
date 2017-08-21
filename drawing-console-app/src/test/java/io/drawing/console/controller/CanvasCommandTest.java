package io.drawing.console.controller;

import io.drawing.console.controller.CanvasCommand;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CanvasCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(CanvasCommand.from("C 1 2"));
    }
}
