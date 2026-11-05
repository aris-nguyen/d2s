package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Concrete Creator for Credit Card payments.
 */
public class CreditCardProcessor extends PaymentProcessor {

    private final String cardNumber;

    public CreditCardProcessor(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CreditCardPayment(cardNumber);
    }
}
