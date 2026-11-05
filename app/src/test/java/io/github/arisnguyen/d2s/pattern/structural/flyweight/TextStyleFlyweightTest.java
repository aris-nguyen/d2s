package io.github.arisnguyen.d2s.pattern.structural.flyweight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Flyweight Pattern Tests - Text Editor Character Styles")
class TextStyleFlyweightTest {

    private TextStyleFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TextStyleFactory();
    }

    @Test
    @DisplayName("Should return the exact same instance for identical intrinsic style parameters")
    void testStyleSharing() {
        TextStyle style1 = factory.getStyle("Arial", 12, "#000000", false, false);
        TextStyle style2 = factory.getStyle("Arial", 12, "#000000", false, false);
        TextStyle style3 = factory.getStyle("Arial", 14, "#000000", true, false);

        assertSame(style1, style2, "Identical styles must share the exact same object reference");
        assertNotSame(style1, style3, "Distinct styles must produce different instances");
        assertEquals(2, factory.getCreatedStylesCount());
    }

    @Test
    @DisplayName("Should format document with thousands of characters sharing minimal style objects")
    void testDocumentWithHighCharacterVolume() {
        TextDocument doc = new TextDocument(factory);

        String sampleHeading = "Title";
        for (int i = 0; i < sampleHeading.length(); i++) {
            doc.appendCharacter(sampleHeading.charAt(i), 0, i, "Helvetica", 18, "#FF0000", true, false);
        }

        String sampleBody = "A".repeat(500);
        for (int i = 0; i < sampleBody.length(); i++) {
            doc.appendCharacter(sampleBody.charAt(i), 1, i, "Arial", 12, "#000000", false, false);
        }

        assertEquals(5 + 500, doc.getCharacterCount());
        // Only 2 TextStyle instances should have been created in total
        assertEquals(2, factory.getCreatedStylesCount());
        assertEquals(sampleHeading + sampleBody, doc.renderText());

        // Verify all body characters point to the exact same style reference
        TextStyle bodyStyle = doc.getCharacters().get(5).getStyle();
        for (int i = 6; i < doc.getCharacterCount(); i++) {
            assertSame(bodyStyle, doc.getCharacters().get(i).getStyle());
        }
    }

    @Test
    @DisplayName("Should throw exception for invalid style or character parameters")
    void testInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () ->
            factory.getStyle(null, 12, "#000000", false, false)
        );
        assertThrows(IllegalArgumentException.class, () ->
            factory.getStyle("Arial", -2, "#000000", false, false)
        );
        assertThrows(IllegalArgumentException.class, () ->
            factory.getStyle("Arial", 12, "   ", false, false)
        );
        assertThrows(IllegalArgumentException.class, () ->
            new FormattedCharacter('c', -1, 0, factory.getStyle("Arial", 12, "#FFF", false, false))
        );
    }
}
