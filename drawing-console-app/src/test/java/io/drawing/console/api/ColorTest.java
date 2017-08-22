package io.drawing.console.api;

import io.drawing.console.api.Color;
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
