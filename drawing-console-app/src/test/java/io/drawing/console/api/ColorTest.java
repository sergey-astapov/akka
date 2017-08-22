package io.drawing.console.api;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class ColorTest {
    @Test
    public void testFrom() {
        assertNotNull(Color.from("~"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testError() {
        Color.from(null);
    }
}
