package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Abstract Factory interface for creating a coherent family of themed UI components.
 */
public interface ThemeFactory {

    Button createButton();

    TextField createTextField();
}
