package io.drawing.console.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class ModelChangedEvent {
    @NonNull
    Character[][] chars;
}
