package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

import java.util.List;

public class VisitorMain {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(
                new Circle(5),
                new Rectangle(4, 6)
        );

        AreaCalculatorVisitor areaCalculator = new AreaCalculatorVisitor();
        for (Shape shape : shapes) {
            shape.accept(areaCalculator);
        }

        System.out.println("Total area calculated by visitor: " + areaCalculator.getTotalArea());
    }
}
