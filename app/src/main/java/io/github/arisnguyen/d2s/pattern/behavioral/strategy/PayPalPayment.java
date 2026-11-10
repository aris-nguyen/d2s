package io.github.arisnguyen.d2s.pattern.behavioral.strategy;

public class PayPalPayment implements PaymentStrategy {
    private final String email;
    private String lastPayment;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        lastPayment = "Paid " + amount + " using PayPal " + email;
    }

    @Override
    public String getPaymentDetails() {
        return lastPayment;
    }
}
