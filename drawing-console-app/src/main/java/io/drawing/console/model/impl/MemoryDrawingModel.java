package io.drawing.console.model.impl;

import io.drawing.console.api.*;
import io.drawing.console.model.*;
import io.drawing.console.view.DrawingView;

import java.util.ArrayList;
import java.util.List;

public class MemoryDrawingModel implements DrawingModel {

    private final DrawingView view;
    private Canvas canvas;
    private List<Figure> figures;
    private List<Bucket> buckets;

    public MemoryDrawingModel(DrawingView view) {
        this.view = view;
        init(null);
    }

    @Override
    public void addCanvas(Canvas canvas) {
        init(canvas);
        updateView();
    }

    @Override
    public void addFigure(Figure figure) {
        validateCanvas();
        validate(figure);
        figures.add(figure);
        updateView();
    }

    @Override
    public void fillBucket(Bucket bucket) {
        validateCanvas();
        validate(bucket);
        buckets.add(bucket);
        updateView();
    }

    private void updateView() {
        view.update(event());
    }

    private void init(Canvas canvas) {
        this.canvas = canvas;
        this.figures = new ArrayList<>();
        this.buckets = new ArrayList<>();
    }

    private void validateCanvas() {
        if (canvas == null) {
            throw new IllegalModelStateException("Canvas is not set");
        }
    }

    private void validate(Canvasable o) {
        if (!o.fitCanvas(canvas)) {
            throw new IllegalModelStateException("Figure is out of canvas size");
        }
    }

    private ModelChangedEvent event() {
        ModelConverter template = new ModelConverter(canvas);

        for (Figure f : figures) {
            template.drawFigure(f);
        }

        for (Bucket b : buckets) {
            template.drawBucket(b);
        }

        return new ModelChangedEvent(template.getChars());
    }
}
