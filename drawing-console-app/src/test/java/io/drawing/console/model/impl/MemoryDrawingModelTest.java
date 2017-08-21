package io.drawing.console.model.impl;

import io.drawing.console.model.*;
import io.drawing.console.view.DrawingView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MemoryDrawingModelTest {
    DrawingModel model;
    DrawingView view;

    @Before
    public void before() {
        view = mock(DrawingView.class);
        model = new MemoryDrawingModel(view);
    }

    @Test
    public void testAddCanvas() {
        model.addCanvas(new Canvas(10, 10));
        verify(view, times(1)).update(any(ModelChangedEvent.class));
    }

    @Test
    public void testAddLine() {
        model.addCanvas(new Canvas(10, 10));
        model.addFigure(new HorizontalLine(new Point(1, 2), new Point(1, 3)));
        verify(view, times(2)).update(any(ModelChangedEvent.class));
    }

    @Test(expected = IllegalModelStateException.class)
    public void testAddLineError() {
        model.addFigure(new HorizontalLine(new Point(1, 2), new Point(1, 3)));
    }

    @Test
    public void testAddRectangle() {
        model.addCanvas(new Canvas(10, 10));
        model.addFigure(new Rectangle(new Point(1, 2), new Point(5, 4)));
        verify(view, times(2)).update(any(ModelChangedEvent.class));
    }

    @Test(expected = IllegalModelStateException.class)
    public void testAddRectangleError() {
        model.addCanvas(new Canvas(10, 10));
        model.addFigure(new Rectangle(new Point(1, 2), new Point(11, 11)));
    }

    @Test
    public void testFillBucket() {
        model.addCanvas(new Canvas(10, 10));
        model.fillBucket(new Bucket(new Point(1, 2), new Color('0')));
        verify(view, times(2)).update(any(ModelChangedEvent.class));
    }

    @Test(expected = IllegalModelStateException.class)
    public void testFillBucketError() {
        model.addCanvas(new Canvas(10, 10));
        model.fillBucket(new Bucket(new Point(1, 12), new Color('0')));
    }
}
