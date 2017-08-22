package io.drawing.console.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Bucket implements Canvasable, Command {
    public static final String BUCKET = "B";

    @NonNull
    Point point;
    @NonNull
    Color color;

    @Override
    public boolean fitCanvas(Canvas c) {
        return point.fitCanvas(c);
    }

    @Builder
    public static Bucket from(Point point, Color color) {
        return new Bucket(point, color);
    }

    public static Bucket from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 4 || !Bucket.BUCKET.equals(arr[0])) {
            log.debug("Not a bucket draw command, input: " + input);
            return null;
        }
        try {
            return Bucket.builder()
                    .point(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .color(Color.from(arr[3]))
                    .build();
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
