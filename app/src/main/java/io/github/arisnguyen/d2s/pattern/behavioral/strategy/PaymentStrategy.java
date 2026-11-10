package io.github.arisnguyen.d2s.pattern.behavioral.strategy;

public interface PaymentStrategy {
    void pay(int amount);
    String getPaymentDetails();
}
