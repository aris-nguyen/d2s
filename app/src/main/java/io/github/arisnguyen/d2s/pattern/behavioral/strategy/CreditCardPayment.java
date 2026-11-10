package io.github.arisnguyen.d2s.pattern.behavioral.strategy;

public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;
    private String lastPayment;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        lastPayment = "Paid " + amount + " using Credit Card " + cardNumber;
    }

    @Override
    public String getPaymentDetails() {
        return lastPayment;
    }
}
