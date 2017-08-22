package io.drawing.console.model.impl;

import io.drawing.console.api.Bucket;
import io.drawing.console.api.Canvas;
import io.drawing.console.api.Figure;
import io.drawing.console.api.ModelChangedEvent;
import io.drawing.console.model.DrawingModel;
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
        validate();
        modelData.add(figure);
        updateView();
    }

    @Override
    public void fill(Bucket bucket) {
        validate();
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

    private void validate() {
        if (modelData == null) {
            throw new IllegalStateException("Canvas is not set");
        }
    }
}
