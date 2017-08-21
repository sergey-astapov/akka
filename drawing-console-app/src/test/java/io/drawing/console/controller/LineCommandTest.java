package io.drawing.console.controller;

import io.drawing.console.controller.LineCommand;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LineCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(LineCommand.from("L 1 2 4 2"));
    }
}
