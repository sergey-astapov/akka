package io.drawing.console.model;

import org.junit.Test;

public class ColorTest {
    @Test(expected = IllegalArgumentException.class)
    public void testReservedX() {
        Color.from("x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReservedVerticalLine() {
        Color.from("|");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReservedHorizontalLine() {
        Color.from("-");
    }
}
