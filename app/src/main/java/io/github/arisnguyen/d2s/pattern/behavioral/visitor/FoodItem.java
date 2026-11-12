package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public class FoodItem implements CartItem {
    private final String name;
    private final double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this);
    }
}
