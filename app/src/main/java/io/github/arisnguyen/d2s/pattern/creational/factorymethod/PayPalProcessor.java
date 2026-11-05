package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Concrete Creator for PayPal payments.
 */
public class PayPalProcessor extends PaymentProcessor {

    private final String email;

    public PayPalProcessor(String email) {
        this.email = email;
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new PayPalPayment(email);
    }
}
