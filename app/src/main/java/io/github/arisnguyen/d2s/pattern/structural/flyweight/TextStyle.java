package io.github.arisnguyen.d2s.pattern.structural.flyweight;

import java.util.Objects;

/**
 * Flyweight: Represents immutable intrinsic formatting state shared across characters.
 */
public final class TextStyle {
    private final String fontFamily;
    private final int fontSize;
    private final String colorHex;
    private final boolean bold;
    private final boolean italic;

    public TextStyle(String fontFamily, int fontSize, String colorHex, boolean bold, boolean italic) {
        if (fontFamily == null || fontFamily.isBlank()) {
            throw new IllegalArgumentException("fontFamily must not be null or blank");
        }
        if (fontSize <= 0) {
            throw new IllegalArgumentException("fontSize must be positive");
        }
        if (colorHex == null || colorHex.isBlank()) {
            throw new IllegalArgumentException("colorHex must not be null or blank");
        }
        this.fontFamily = fontFamily.trim();
        this.fontSize = fontSize;
        this.colorHex = colorHex.trim().toUpperCase();
        this.bold = bold;
        this.italic = italic;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getColorHex() {
        return colorHex;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic() {
        return italic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextStyle that)) return false;
        return fontSize == that.fontSize &&
               bold == that.bold &&
               italic == that.italic &&
               fontFamily.equalsIgnoreCase(that.fontFamily) &&
               colorHex.equalsIgnoreCase(that.colorHex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontFamily.toLowerCase(), fontSize, colorHex.toLowerCase(), bold, italic);
    }

    @Override
    public String toString() {
        return String.format("%s-%dpt-%s%s%s",
            fontFamily, fontSize, colorHex, bold ? "-B" : "", italic ? "-I" : "");
    }
}
