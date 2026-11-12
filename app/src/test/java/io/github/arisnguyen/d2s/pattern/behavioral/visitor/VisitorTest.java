package io.github.arisnguyen.d2s.pattern.behavioral.visitor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitorTest {

    @Test
    void testTaxAndShippingCalculationAcrossDifferentItemTypes() {
        List<CartItem> items = List.of(
                new BookItem("Domain-Driven Design", 50.0),      // Tax: 2.5, Ship: 2.0
                new ElectronicItem("Wireless Mouse", 80.0),      // Tax: 12.0, Ship: 10.0
                new FoodItem("Dark Chocolate", 20.0)             // Tax: 0.0, Ship: 5.0
        );

        TaxAndShippingCalculatorVisitor visitor = new TaxAndShippingCalculatorVisitor();
        for (CartItem item : items) {
            item.accept(visitor);
        }

        assertEquals(14.5, visitor.getTotalTax(), 0.001);
        assertEquals(17.0, visitor.getTotalShipping(), 0.001);
        assertEquals(31.5, visitor.getTotalExtraCost(), 0.001);
    }
}
