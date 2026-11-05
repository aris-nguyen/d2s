package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ThemeFactoryTest {

    @Test
    @DisplayName("DarkThemeFactory should produce a consistent family of dark components")
    public void testDarkThemeFactory() {
        ThemeFactory factory = new DarkThemeFactory();
        Button button = factory.createButton();
        TextField textField = factory.createTextField();

        assertNotNull(button);
        assertNotNull(textField);
        assertInstanceOf(DarkButton.class, button);
        assertInstanceOf(DarkTextField.class, textField);

        assertTrue(button.render().contains("Dark Button"));
        assertTrue(textField.render().contains("Dark TextField"));
    }

    @Test
    @DisplayName("LightThemeFactory should produce a consistent family of light components")
    public void testLightThemeFactory() {
        ThemeFactory factory = new LightThemeFactory();
        Button button = factory.createButton();
        TextField textField = factory.createTextField();

        assertNotNull(button);
        assertNotNull(textField);
        assertInstanceOf(LightButton.class, button);
        assertInstanceOf(LightTextField.class, textField);

        assertTrue(button.render().contains("Light Button"));
        assertTrue(textField.render().contains("Light TextField"));
    }

    @Test
    @DisplayName("ApplicationUI configured with DarkThemeFactory should render dark UI family")
    public void testApplicationUIWithDarkTheme() {
        ApplicationUI app = new ApplicationUI(new DarkThemeFactory());
        String screen = app.renderScreen();

        assertTrue(screen.contains("Dark TextField"));
        assertTrue(screen.contains("Dark Button"));
    }

    @Test
    @DisplayName("ApplicationUI configured with LightThemeFactory should render light UI family")
    public void testApplicationUIWithLightTheme() {
        ApplicationUI app = new ApplicationUI(new LightThemeFactory());
        String screen = app.renderScreen();

        assertTrue(screen.contains("Light TextField"));
        assertTrue(screen.contains("Light Button"));
    }

    @Test
    @DisplayName("ApplicationUI should reject null factory")
    public void testNullFactory() {
        assertThrows(NullPointerException.class, () -> new ApplicationUI(null));
    }
}
