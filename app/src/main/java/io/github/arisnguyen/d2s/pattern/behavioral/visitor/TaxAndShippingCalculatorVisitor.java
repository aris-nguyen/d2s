package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

public class TaxAndShippingCalculatorVisitor implements CartItemVisitor {
    private double totalTax = 0.0;
    private double totalShipping = 0.0;

    @Override
    public void visit(BookItem book) {
        // Books: Low tax (5%), flat shipping ($2.0)
        totalTax += book.getPrice() * 0.05;
        totalShipping += 2.0;
    }

    @Override
    public void visit(ElectronicItem electronic) {
        // Electronics: Standard luxury tax (15%), fragile shipping ($10.0)
        totalTax += electronic.getPrice() * 0.15;
        totalShipping += 10.0;
    }

    @Override
    public void visit(FoodItem food) {
        // Food: Zero tax (0%), cold shipping ($5.0)
        totalTax += 0.0;
        totalShipping += 5.0;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public double getTotalShipping() {
        return totalShipping;
    }

    public double getTotalExtraCost() {
        return totalTax + totalShipping;
    }
}
