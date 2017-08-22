package io.drawing.console.controller;

import io.drawing.console.api.Bucket;
import io.drawing.console.api.Canvas;
import io.drawing.console.api.Line;
import io.drawing.console.api.Rectangle;
import io.drawing.console.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

public class BaseDrawingControllerTest {
    private BaseDrawingController sut;
    private DrawingModel model;

    @Before
    public void before() {
        model = Mockito.mock(DrawingModel.class);
        sut = new BaseDrawingController(model) {
            @Override
            public void userLoop() {

            }
        };
    }

    @Test
    public void testQuit() {
        assertFalse(sut.process("Q"));
    }

    @Test
    public void testCanvas() {
        assertTrue(sut.process("C 1 2"));
        Mockito.verify(model, times(1)).add(Mockito.any(Canvas.class));
    }

    @Test
    public void testLine() {
        assertTrue(sut.process("L 1 2 1 4"));
        Mockito.verify(model, times(1)).add(Mockito.any(Line.class));
    }

    @Test
    public void testRectangle() {
        assertTrue(sut.process("R 1 2 3 4"));
        Mockito.verify(model, times(1)).add(Mockito.any(Rectangle.class));
    }

    @Test
    public void testBucketFill() {
        assertTrue(sut.process("B 1 2 3"));
        Mockito.verify(model, times(1)).fill(Mockito.any(Bucket.class));
    }
}
