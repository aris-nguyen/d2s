package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

import java.util.Objects;

/**
 * Client class decoupled from concrete theme implementations.
 * Ensures all rendered components belong to the same visual family.
 */
public class ApplicationUI {

    private final Button button;
    private final TextField textField;

    public ApplicationUI(ThemeFactory factory) {
        Objects.requireNonNull(factory, "ThemeFactory cannot be null");
        this.button = factory.createButton();
        this.textField = factory.createTextField();
    }

    public String renderScreen() {
        return textField.render() + " | " + button.render();
    }

    public Button getButton() {
        return button;
    }

    public TextField getTextField() {
        return textField;
    }
}
