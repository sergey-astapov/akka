package io.drawing.console.controller;

import io.drawing.console.controller.BucketFillCommand;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BucketFillCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(BucketFillCommand.from("B 1 2 3"));
    }
}
