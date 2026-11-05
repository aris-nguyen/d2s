package io.github.arisnguyen.d2s.pattern.structural.flyweight;

import java.util.Objects;

/**
 * Context: Contains extrinsic state (character value, coordinates) and references the shared Flyweight (TextStyle).
 */
public class FormattedCharacter {
    private final char character;
    private final int row;
    private final int col;
    private final TextStyle style;

    public FormattedCharacter(char character, int row, int col, TextStyle style) {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException("Coordinates must not be negative");
        }
        this.character = character;
        this.row = row;
        this.col = col;
        this.style = Objects.requireNonNull(style, "style must not be null");
    }

    public char getCharacter() {
        return character;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public TextStyle getStyle() {
        return style;
    }
}
