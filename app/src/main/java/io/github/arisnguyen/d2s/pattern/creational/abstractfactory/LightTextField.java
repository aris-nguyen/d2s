package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Product: TextField styled for Light Theme.
 */
public class LightTextField implements TextField {

    @Override
    public String render() {
        return "Rendering Light TextField [background=#FAFAFA, border=#CCCCCC]";
    }
}
