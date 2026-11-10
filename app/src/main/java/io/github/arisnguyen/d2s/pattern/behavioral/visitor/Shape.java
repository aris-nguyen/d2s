package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public interface Shape {
    void accept(ShapeVisitor visitor);
}
