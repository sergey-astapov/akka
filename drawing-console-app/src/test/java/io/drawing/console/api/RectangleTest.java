package io.drawing.console.api;

import io.drawing.console.api.Rectangle;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RectangleTest {
    @Test
    public void testFrom() {
        assertNotNull(Rectangle.from("R 1 2 3 4"));
    }

    @Test
    public void testFromNull() {
        assertNull(Rectangle.from("B 1 2 4 2"));
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testFromError() {
        Rectangle.from("R * 2 3 4");
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testPositiveOnly() {
        Rectangle.from("R 1 2 -4 4");
    }

    @Test
    public void testFitCanvas() {
        assertTrue(Rectangle.from(new Point(1,  2), new Point(3, 4)).fitCanvas(10, 10));
    }

    @Test
    public void testNotFitCanvas() {
        assertFalse(Rectangle.from(new Point(1,  2), new Point(11, 4)).fitCanvas(10, 10));
    }
}
