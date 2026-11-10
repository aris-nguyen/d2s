package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public interface ShapeVisitor {
    void visit(Circle circle);
    void visit(Rectangle rectangle);
}
