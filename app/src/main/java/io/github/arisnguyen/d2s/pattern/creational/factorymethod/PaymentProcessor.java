package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Creator abstract class declaring the factory method.
 * Subclasses determine which PaymentMethod to instantiate.
 */
public abstract class PaymentProcessor {

    /**
     * Factory Method.
     */
    public abstract PaymentMethod createPaymentMethod();

    /**
     * Business operation decoupled from concrete payment implementation.
     */
    public String processOrder(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        PaymentMethod paymentMethod = createPaymentMethod();
        return paymentMethod.pay(amount);
    }
}
