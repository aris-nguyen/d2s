package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Concrete Product: Credit Card payment method.
 */
public class CreditCardPayment implements PaymentMethod {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " using Credit Card ending with " + cardNumber.substring(Math.max(0, cardNumber.length() - 4));
    }
}
