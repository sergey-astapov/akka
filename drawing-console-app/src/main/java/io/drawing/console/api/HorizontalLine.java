package io.drawing.console.api;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HorizontalLine extends Line {
    public HorizontalLine(Point start, Point end) {
        super(start, end);
    }
}
