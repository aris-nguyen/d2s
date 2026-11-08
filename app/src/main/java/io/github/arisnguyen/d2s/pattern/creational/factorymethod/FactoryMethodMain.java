package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

public class FactoryMethodMain {
    public static void main(String[] args) {
        PaymentProcessor cardProcessor = new CreditCardProcessor("1234567890123456");
        System.out.println(cardProcessor.processOrder(150.0));

        PaymentProcessor paypalProcessor = new PayPalProcessor("user@example.com");
        System.out.println(paypalProcessor.processOrder(85.5));
    }
}

