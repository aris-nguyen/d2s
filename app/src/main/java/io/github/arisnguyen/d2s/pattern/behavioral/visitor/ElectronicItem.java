package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public class ElectronicItem implements CartItem {
    private final String model;
    private final double price;

    public ElectronicItem(String model, double price) {
        this.model = model;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this);
    }
}
