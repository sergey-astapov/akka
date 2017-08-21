package io.drawing.console.controller;

import io.drawing.console.controller.RectangleCommand;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RectangleCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(RectangleCommand.from("R 1 2 3 4"));
    }
}
