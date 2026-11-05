package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

/**
 * Product interface for payment processing.
 */
public interface PaymentMethod {

    String pay(double amount);
}
