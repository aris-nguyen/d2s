package io.github.arisnguyen.d2s.pattern.structural.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Factory: Manages a cache of reusable TextStyle objects to avoid redundant allocations.
 */
public class TextStyleFactory {
    private final Map<String, TextStyle> cache = new HashMap<>();

    public TextStyle getStyle(String fontFamily, int fontSize, String colorHex, boolean bold, boolean italic) {
        String key = String.format("%s:%d:%s:%b:%b",
            fontFamily != null ? fontFamily.toLowerCase().trim() : "",
            fontSize,
            colorHex != null ? colorHex.toLowerCase().trim() : "",
            bold,
            italic
        );

        return cache.computeIfAbsent(key, k -> new TextStyle(fontFamily, fontSize, colorHex, bold, italic));
    }

    public int getCreatedStylesCount() {
        return cache.size();
    }

    public void clearCache() {
        cache.clear();
    }
}
