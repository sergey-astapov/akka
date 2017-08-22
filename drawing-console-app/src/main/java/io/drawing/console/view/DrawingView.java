package io.drawing.console.view;

import io.drawing.console.api.ModelChangedEvent;

public interface DrawingView {
    void update(ModelChangedEvent event);
}
