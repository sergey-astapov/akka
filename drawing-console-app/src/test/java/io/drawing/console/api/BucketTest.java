package io.drawing.console.api;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

public class BucketTest {
    @Test
    public void testFrom() {
        assertNotNull(Bucket.from("B 1 2 3"));
    }

    @Test
    public void testFromNull() {
        assertNull(Bucket.from("C 1 2 3"));
    }

    @Test(expected = IllegalCommandFormatException.class)
    public void testFromError() {
        Bucket.from("B * 2 3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositiveOnly() {
        Bucket.from(new Point(-1,  -2), new Color(' '));
    }

    @Test
    public void testFitCanvas() {
        assertTrue(Bucket.from(new Point(10, 10), new Color('3')).fitCanvas(10, 10));
    }

    @Test
    public void testNotFitCanvas() {
        assertFalse(Bucket.from(new Point(11, 11), new Color('3')).fitCanvas(10, 10));
    }
}
