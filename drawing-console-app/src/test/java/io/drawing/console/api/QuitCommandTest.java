package io.drawing.console.api;

import io.drawing.console.api.Quit;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class QuitCommandTest {
    @Test
    public void testFrom() {
        assertNotNull(Quit.from("Q"));
    }
}
