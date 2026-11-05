package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Concrete Product: PayPal payment method.
 */
public class PayPalPayment implements PaymentMethod {

    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " via PayPal account [" + email + "]";
    }
}
