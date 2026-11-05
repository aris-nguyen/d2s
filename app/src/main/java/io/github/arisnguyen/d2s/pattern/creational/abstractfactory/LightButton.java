package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Product: Button styled for Light Theme.
 */
public class LightButton implements Button {

    @Override
    public String render() {
        return "Rendering Light Button [background=#FFFFFF, text=#000000]";
    }
}
