package io.drawing.console.controller;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class QuitCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(QuitCommand.from("Q"));
    }
}
