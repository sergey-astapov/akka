package io.drawing.console.api;

import io.drawing.console.api.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RectangleTest {
    @Test
    public void testFrom() {
        assertNotNull(Rectangle.from("R 1 2 3 4"));
    }
}
