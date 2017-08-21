package io.drawing.console.model;

import io.drawing.console.model.Canvas;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CanvasTest {
    @Test
    public void testFrom() {
        assertNotNull(Canvas.builder().width(1).height(2).build());
    }
}
