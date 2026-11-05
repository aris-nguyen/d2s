package io.github.arisnguyen.d2s.pattern.creational.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailTemplateTest {

    @Test
    @DisplayName("Should clone template independently without affecting prototype state")
    public void testCloneIndependence() {
        EmailTemplate prototype = new EmailTemplate(
                "Welcome!",
                "Hello {{name}},",
                "Best regards,\nSupport Team",
                "Thanks for joining our platform.",
                new ArrayList<>(List.of("onboarding", "welcome"))
        );

        EmailTemplate clone = prototype.copy();

        assertNotNull(clone);
        assertNotSame(prototype, clone);
        assertNotSame(prototype.getTags(), clone.getTags());

        // Modify clone
        clone.setContent("Custom discount for joining today!");
        clone.addTag("promo-2026");

        assertEquals(3, clone.getTags().size());
        assertEquals(2, prototype.getTags().size());
        assertFalse(prototype.getTags().contains("promo-2026"));
        assertEquals("Thanks for joining our platform.", prototype.getContent());
    }

    @Test
    @DisplayName("Should retrieve and customize clone from TemplateRegistry")
    public void testTemplateRegistry() {
        TemplateRegistry registry = new TemplateRegistry();
        EmailTemplate baseTemplate = new EmailTemplate(
                "Invoice Notification",
                "Dear {{name}},",
                "Company Finance Dept.",
                "Your payment of $100 has been received.",
                List.of("finance", "receipt")
        );

        registry.register("invoice", baseTemplate);
        assertTrue(registry.contains("invoice"));

        EmailTemplate clone = registry.get("invoice");
        assertNotSame(baseTemplate, clone);

        String rendered = clone.format("Alice");
        assertTrue(rendered.contains("Dear Alice,"));
        assertTrue(rendered.contains("Your payment of $100 has been received."));
    }

    @Test
    @DisplayName("Registry should throw NoSuchElementException for unregistered keys")
    public void testRegistryUnknownKeyThrowsException() {
        TemplateRegistry registry = new TemplateRegistry();
        assertThrows(NoSuchElementException.class, () -> registry.get("unknown-key"));
    }
}
