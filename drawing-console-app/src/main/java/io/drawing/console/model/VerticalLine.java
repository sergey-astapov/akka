package io.drawing.console.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VerticalLine extends Line {
    public VerticalLine(Point start, Point end) {
        super(start, end);
    }
}
