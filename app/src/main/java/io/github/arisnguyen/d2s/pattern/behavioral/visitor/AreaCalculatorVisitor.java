package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public class AreaCalculatorVisitor implements ShapeVisitor {
    private double totalArea = 0.0;

    @Override
    public void visit(Circle circle) {
        totalArea += Math.PI * circle.getRadius() * circle.getRadius();
    }

    @Override
    public void visit(Rectangle rectangle) {
        totalArea += rectangle.getWidth() * rectangle.getHeight();
    }

    public double getTotalArea() {
        return totalArea;
    }
}
