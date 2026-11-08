package io.github.arisnguyen.d2s.pattern.structural.flyweight;

public class FlyweightMain {
    public static void main(String[] args) {
        TextStyleFactory factory = new TextStyleFactory();
        TextDocument document = new TextDocument(factory);

        document.appendCharacter('H', 0, 0, "Arial", 12, "#000000", false, false);
        document.appendCharacter('i', 0, 1, "Arial", 12, "#000000", false, false);

        System.out.println("Rendered: " + document.renderText());
        System.out.println("Cached styles count: " + factory.getCreatedStylesCount());
    }
}
