package io.drawing.console.api;

import com.sun.istack.internal.NotNull;
import lombok.Value;

@Value
public class ModelChangedEvent {
    @NotNull
    Character[][] chars;
}
