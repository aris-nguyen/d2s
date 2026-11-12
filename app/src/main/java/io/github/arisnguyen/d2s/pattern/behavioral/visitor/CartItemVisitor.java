package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public interface CartItemVisitor {
    void visit(BookItem book);
    void visit(ElectronicItem electronic);
    void visit(FoodItem food);
}
