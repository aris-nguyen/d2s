package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Factory for Dark Theme components.
 */
public class DarkThemeFactory implements ThemeFactory {

    @Override
    public Button createButton() {
        return new DarkButton();
    }

    @Override
    public TextField createTextField() {
        return new DarkTextField();
    }
}
