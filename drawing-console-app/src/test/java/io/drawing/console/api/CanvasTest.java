package io.drawing.console.api;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

public class CanvasTest {
    @Test
    public void testFrom() {
        assertNotNull(Canvas.from("C 1 2"));
    }

    @Test
    public void testFromNull() {
        assertNull(Canvas.from("B 1 2"));
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testFromError() {
        Canvas.from("C * 2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveOnly() {
        Canvas.from(-1,  -2);
    }

    @Test
    public void testCanvas() {
        assertNotNull(Canvas.builder().width(1).height(2).build());
    }
}
