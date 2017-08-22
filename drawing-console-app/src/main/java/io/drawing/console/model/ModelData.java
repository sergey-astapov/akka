package io.drawing.console.model;

import io.drawing.console.api.*;
import lombok.Value;

@Value
public class ModelData {

    public static final char X_CHAR = 'x';
    public static final char SPACE_CHAR = ' ';
    public static final int MAX_AREA = 80 * 80;

    int width;
    int height;
    Character[][] chars;

    public ModelData(int width, int height) {
        if (width * height > MAX_AREA) {
            throw new IllegalArgumentException("width(" + width + ") * height(" + height + ") should be less than " + MAX_AREA);
        }
        Character[][] chars = new Character[height + 2][width + 2];
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (i == 0 || i == chars.length - 1) {
                    chars[i][j] = '-';
                } else if (j == 0 || j == chars[i].length - 1) {
                    chars[i][j] = '|';
                } else {
                    chars[i][j] = SPACE_CHAR;
                }
            }
        }
        this.width = width;
        this.height = height;
        this.chars = chars;
    }

    public Character[][] getChars() {
        return chars.clone();
    }

    public void add(Figure f) {
        validate(f);

        if (f instanceof HorizontalLine) {
            Line line = (HorizontalLine) f;
            Point start = line.getStart();
            Point end = line.getEnd();
            for (int j = start.getX(); j <= end.getX(); j++) {
                chars[start.getY()][j] = X_CHAR;
            }
        } else if (f instanceof VerticalLine) {
            Line line = (VerticalLine) f;
            Point start = line.getStart();
            Point end = line.getEnd();
            for (int i = start.getY(); i <= end.getY(); i++) {
                chars[i][start.getX()] = X_CHAR;
            }
        } else if (f instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) f;
            Point upperLeftCorner = rectangle.getUpperLeftCorner();
            Point lowerRightCorner = rectangle.getLowerRightCorner();
            for (int j = upperLeftCorner.getX(); j <= lowerRightCorner.getX(); j++) {
                chars[upperLeftCorner.getY()][j] = X_CHAR;
                chars[lowerRightCorner.getY()][j] = X_CHAR;
            }
            for (int i = upperLeftCorner.getY() + 1; i <= lowerRightCorner.getY() - 1; i++) {
                chars[i][upperLeftCorner.getX()] = X_CHAR;
                chars[i][lowerRightCorner.getX()] = X_CHAR;
            }
        } else {
            throw new IllegalArgumentException("Unsupported figure: " + f.getClass());
        }
    }

    public void fill(Bucket b) {
        validate(b);

        Point point = b.getPoint();
        int x = point.getX();
        int y = point.getY();
        floodFill(x, y, chars[y][x], b.getColor().getValue());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Character[] aChar : chars) {
            for (Character anAChar : aChar) {
                sb.append(anAChar);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void validate(Canvasable o) {
        if (!o.fitCanvas(width, height)) {
            throw new IllegalArgumentException("Figure doesn't fit canvas size");
        }
    }

    private void floodFill(int x, int y, Character targetColor, Character replacementColor) {
        if (targetColor.equals(replacementColor)) {
            return;
        }
        if (!chars[y][x].equals(targetColor)) {
            return;
        }
        chars[y][x] = replacementColor;
        floodFill(x, y - 1, targetColor, replacementColor);
        floodFill(x, y + 1, targetColor, replacementColor);
        floodFill(x - 1, y, targetColor, replacementColor);
        floodFill(x + 1, y, targetColor, replacementColor);
    }
}
