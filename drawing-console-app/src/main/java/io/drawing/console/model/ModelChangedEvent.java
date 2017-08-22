package io.drawing.console.model;

import io.drawing.console.api.Bucket;
import io.drawing.console.api.Canvas;
import io.drawing.console.api.Figure;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class ModelChangedEvent {
    @NonNull
    Canvas canvas;
    @NonNull
    List<Figure> figures;
    @NonNull
    List<Bucket> buckets;
}
