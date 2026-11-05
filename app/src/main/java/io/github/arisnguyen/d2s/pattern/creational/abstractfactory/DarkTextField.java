package io.github.arisnguyen.d2s.pattern.creational.abstractfactory;

/**
 * Concrete Product: TextField styled for Dark Theme.
 */
public class DarkTextField implements TextField {

    @Override
    public String render() {
        return "Rendering Dark TextField [background=#1E1E1E, border=#333333]";
    }
}
