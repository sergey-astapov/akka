package io.drawing.console.api;

import io.drawing.console.api.Line;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LineTest {
    @Test
    public void testFrom() {
        assertNotNull(Line.from("L 1 2 4 2"));
    }

    @Test
    public void testFromNull() {
        assertNull(Line.from("B 1 2 4 2"));
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testFromError() {
        Line.from("L * 2 4 2");
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testPositiveOnly() {
        Line.from("L -1 2 -1 4");
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testHorizontVertOnly() {
        Line.from("L 1 2 4 4");
    }

    @Test
    public void testFitCanvas() {
        assertTrue(Line.from(new Point(1,  2), new Point(4, 2)).fitCanvas(10, 10));
    }

    @Test
    public void testNotFitCanvas() {
        assertFalse(Line.from(new Point(11,  2), new Point(11, 4)).fitCanvas(10, 10));
    }
}
