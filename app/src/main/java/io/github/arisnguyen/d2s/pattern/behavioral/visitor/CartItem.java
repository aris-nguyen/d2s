package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public interface CartItem {
    void accept(CartItemVisitor visitor);
}
