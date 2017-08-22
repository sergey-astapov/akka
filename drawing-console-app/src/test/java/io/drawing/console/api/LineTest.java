package io.drawing.console.api;

import io.drawing.console.api.Line;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LineTest {
    @Test
    public void testFrom() {
        assertNotNull(Line.from("L 1 2 4 2"));
    }
}
