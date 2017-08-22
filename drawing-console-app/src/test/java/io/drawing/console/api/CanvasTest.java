package io.drawing.console.api;

import io.drawing.console.api.Canvas;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CanvasTest {
    @Test
    public void testFrom() {
        assertNotNull(Canvas.from("C 1 2"));
    }

    @Test
    public void testCanvas() {
        assertNotNull(Canvas.builder().width(1).height(2).build());
    }
}
