package io.drawing.console.model;

import io.drawing.console.api.*;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ModelDataTest {
    public static final String CANVAS =
        "----------------------\n" +
        "|                    |\n" +
        "|                    |\n" +
        "|                    |\n" +
        "|                    |\n" +
        "----------------------\n";

    public static final String LINE =
        "----------------------\n"+
        "|                    |\n"+
        "|xxxxxx              |\n"+
        "|     x              |\n"+
        "|     x              |\n"+
        "----------------------\n";

    public static final String RECTANGLE =
        "----------------------\n" +
        "|             xxxxx  |\n" +
        "|             x   x  |\n" +
        "|             xxxxx  |\n" +
        "|                    |\n" +
        "----------------------\n";

    public static final String BUCKET =
        "----------------------\n"+
        "|oooooooooooooxxxxxoo|\n"+
        "|ooooooooooooox   xoo|\n"+
        "|oooooooooooooxxxxxoo|\n"+
        "|oooooooooooooooooooo|\n"+
        "----------------------\n";

    @Test
    public void testCanvas() {
        ModelData sut = new ModelData(20, 4);
        assertThat(sut.toString(), is(CANVAS));
    }

    @Test
    public void testLine() {
        ModelData sut = new ModelData(20, 4);
        sut.add(Line.from(new Point(1, 2), new Point(6, 2)));
        sut.add(Line.from(new Point(6, 3), new Point(6, 4)));
        assertThat(sut.toString(), is(LINE));
    }

    @Test
    public void testRectangle() {
        ModelData sut = new ModelData(20, 4);
        sut.add(Rectangle.from(new Point(14, 1), new Point(18, 3)));
        assertThat(sut.toString(), is(RECTANGLE));
    }

    @Test
    public void testBucket() {
        ModelData sut = new ModelData(20, 4);
        sut.add(Rectangle.from(new Point(14, 1), new Point(18, 3)));
        sut.fill(Bucket.from(new Point(10, 3), Color.from("o")));
        assertThat(sut.toString(), is(BUCKET));
    }
}
