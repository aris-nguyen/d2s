package io.github.arisnguyen.d2s.pattern.behavioral.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StrategyTest {

    @Test
    void testCreditCardPayment() {
        ShoppingCart cart = new ShoppingCart();
        CreditCardPayment cc = new CreditCardPayment("1111-2222-3333-4444");
        cart.setPaymentStrategy(cc);

        cart.checkout(500);
        assertEquals("Paid 500 using Credit Card 1111-2222-3333-4444", cc.getPaymentDetails());
    }

    @Test
    void testPayPalPayment() {
        ShoppingCart cart = new ShoppingCart();
        PayPalPayment pp = new PayPalPayment("test@domain.com");
        cart.setPaymentStrategy(pp);

        cart.checkout(150);
        assertEquals("Paid 150 using PayPal test@domain.com", pp.getPaymentDetails());
    }

    @Test
    void testCheckoutWithoutStrategy() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalStateException.class, () -> cart.checkout(100));
    }
}
