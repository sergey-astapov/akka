package io.drawing.console.model;

import io.drawing.console.api.Bucket;
import io.drawing.console.api.Canvas;
import io.drawing.console.api.Figure;

public interface DrawingModel {
    void add(Canvas canvas);

    void add(Figure figure);

    void fill(Bucket bucket);
}
