package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitorTest {

    @Test
    void testAreaCalculatorVisitor() {
        Circle circle = new Circle(10); // Area = 100 * PI = 314.159...
        Rectangle rectangle = new Rectangle(5, 4); // Area = 20

        AreaCalculatorVisitor visitor = new AreaCalculatorVisitor();
        circle.accept(visitor);
        rectangle.accept(visitor);

        double expected = (Math.PI * 100) + 20;
        assertEquals(expected, visitor.getTotalArea(), 0.0001);
    }
}
