package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Product: Button styled for Dark Theme.
 */
public class DarkButton implements Button {

    @Override
    public String render() {
        return "Rendering Dark Button [background=#121212, text=#FFFFFF]";
    }
}
