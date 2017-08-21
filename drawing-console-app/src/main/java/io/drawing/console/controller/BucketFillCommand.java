package io.drawing.console.controller;

import io.drawing.console.model.Bucket;
import io.drawing.console.model.Color;
import io.drawing.console.model.Point;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class BucketFillCommand implements Command {
    @NonNull
    Bucket bucket;

    public static BucketFillCommand from(String input) {
        String[] arr = input.split("\\s+");
        if (arr.length != 4 || !Bucket.BUCKET.equals(arr[0])) {
            log.debug("Not a bucket draw command, input: " + input);
            return null;
        }
        try {
            Bucket bucket = Bucket.builder()
                    .point(new Point(Integer.parseInt(arr[1]), Integer.parseInt(arr[2])))
                    .color(Color.from(arr[3]))
                    .build();
            return new BucketFillCommand(bucket);
        } catch (Exception e) {
            throw new IllegalCommandFormatException(input);
        }
    }
}
