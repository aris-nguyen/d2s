package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public class BookItem implements CartItem {
    private final String title;
    private final double price;

    public BookItem(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void accept(CartItemVisitor visitor) {
        visitor.visit(this);
    }
}
