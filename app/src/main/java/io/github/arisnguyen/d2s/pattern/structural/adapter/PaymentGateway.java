package io.github.arisnguyen.d2s.pattern.structural.adapter;

/**
 * Target interface expected by modern client applications.
 */
public interface PaymentGateway {
    PaymentResponse processPayment(PaymentRequest request);
}
