package io.drawing.console.model;

import io.drawing.console.api.Bucket;
import io.drawing.console.api.Canvas;
import io.drawing.console.api.Figure;

public interface DrawingModel {
    void addCanvas(Canvas canvas);

    void addFigure(Figure figure);

    void fillBucket(Bucket bucket);
}
