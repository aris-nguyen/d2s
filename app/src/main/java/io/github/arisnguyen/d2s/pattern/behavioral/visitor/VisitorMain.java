package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

import java.util.List;

public class VisitorMain {
    public static void main(String[] args) {
        List<CartItem> cart = List.of(
                new BookItem("Clean Code by Robert C. Martin", 40.0),
                new ElectronicItem("Mechanical Keyboard", 120.0),
                new FoodItem("Organic Coffee Beans", 25.0)
        );

        TaxAndShippingCalculatorVisitor feeCalculator = new TaxAndShippingCalculatorVisitor();
        for (CartItem item : cart) {
            item.accept(feeCalculator);
        }

        System.out.printf("Total Tax: $%.2f%n", feeCalculator.getTotalTax());
        System.out.printf("Total Shipping: $%.2f%n", feeCalculator.getTotalShipping());
        System.out.printf("Total Additional Costs: $%.2f%n", feeCalculator.getTotalExtraCost());
    }
}
