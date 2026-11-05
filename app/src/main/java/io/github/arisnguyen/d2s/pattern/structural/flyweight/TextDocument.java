package io.github.arisnguyen.d2s.pattern.structural.flyweight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Client class representing a document composed of formatted characters.
 */
public class TextDocument {
    private final List<FormattedCharacter> characters = new ArrayList<>();
    private final TextStyleFactory styleFactory;

    public TextDocument(TextStyleFactory styleFactory) {
        this.styleFactory = Objects.requireNonNull(styleFactory, "styleFactory must not be null");
    }

    public void appendCharacter(char c, int row, int col,
                                String fontFamily, int fontSize, String colorHex,
                                boolean bold, boolean italic) {
        TextStyle sharedStyle = styleFactory.getStyle(fontFamily, fontSize, colorHex, bold, italic);
        characters.add(new FormattedCharacter(c, row, col, sharedStyle));
    }

    public int getCharacterCount() {
        return characters.size();
    }

    public List<FormattedCharacter> getCharacters() {
        return Collections.unmodifiableList(characters);
    }

    public String renderText() {
        StringBuilder sb = new StringBuilder();
        for (FormattedCharacter fc : characters) {
            sb.append(fc.getCharacter());
        }
        return sb.toString();
    }
}
