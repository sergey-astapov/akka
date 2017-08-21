package io.drawing.console.view;

import io.drawing.console.model.ModelChangedEvent;

public interface DrawingView {
    void update(ModelChangedEvent event);
}
