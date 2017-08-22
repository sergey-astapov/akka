package io.drawing.console.api;

import org.junit.Test;

public class ColorTest {
    @Test(expected = IllegalArgumentException.class)
    public void testError() {
        Color.from(null);
    }
}
