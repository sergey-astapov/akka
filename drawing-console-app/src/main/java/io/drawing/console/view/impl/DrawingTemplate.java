package io.drawing.console.view.impl;

import io.drawing.console.model.*;

public class DrawingTemplate {

    public static final char X_CHAR = 'x';
    public static final char SPACE_CHAR = ' ';
    private final Character[][] chars;

    public Character[][] getChars() {
        return chars;
    }

    public DrawingTemplate(Canvas c) {
        Character[][] chars = new Character[c.getHeight() + 2][c.getWidth() + 2];
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
        this.chars = chars;
    }

    public void drawFigure(Figure f) {
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

    public void drawBucket(Bucket b) {
        Point point = b.getPoint();
        int x = point.getX();
        int y = point.getY();
        floodFill(x, y, chars[y][x], b.getColor().getValue());
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
