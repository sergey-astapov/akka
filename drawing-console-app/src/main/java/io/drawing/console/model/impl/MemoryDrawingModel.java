package io.drawing.console.model.impl;

import io.drawing.console.api.*;
import io.drawing.console.model.DrawingModel;
import io.drawing.console.model.IllegalModelStateException;
import io.drawing.console.model.ModelData;
import io.drawing.console.view.DrawingView;

public class MemoryDrawingModel implements DrawingModel {

    private ModelData modelData;
    private final DrawingView view;

    public MemoryDrawingModel(DrawingView view) {
        this.view = view;
        init(null);
    }

    @Override
    public void add(Canvas canvas) {
        init(canvas);
        updateView();
    }

    @Override
    public void add(Figure figure) {
        validateCanvas();
        validate(figure);
        modelData.add(figure);
        updateView();
    }

    @Override
    public void fill(Bucket bucket) {
        validateCanvas();
        validate(bucket);
        modelData.fill(bucket);
        updateView();
    }

    private void updateView() {
        view.update(new ModelChangedEvent(modelData.getChars()));
    }

    private void init(Canvas canvas) {
        this.modelData = null;
        if (canvas != null) {
            this.modelData = new ModelData(canvas.getWidth(), canvas.getHeight());
        }
    }

    private void validateCanvas() {
        if (modelData == null) {
            throw new IllegalModelStateException("Canvas is not set");
        }
    }

    private void validate(Canvasable o) {
        if (!o.fitCanvas(modelData.getWidth(), modelData.getHeight())) {
            throw new IllegalModelStateException("Figure doesn't fit canvas size");
        }
    }
}
