package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Factory for Light Theme components.
 */
public class LightThemeFactory implements ThemeFactory {

    @Override
    public Button createButton() {
        return new LightButton();
    }

    @Override
    public TextField createTextField() {
        return new LightTextField();
    }
}
