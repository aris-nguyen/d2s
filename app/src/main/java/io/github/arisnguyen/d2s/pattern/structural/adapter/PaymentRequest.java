package io.github.arisnguyen.d2s.pattern.structural.adapter;

import java.util.Objects;

/**
 * Modern payment request representation.
 */
public class PaymentRequest {
    private final String orderId;
    private final double amount;
    private final String currency;
    private final String customerEmail;

    public PaymentRequest(String orderId, double amount, String currency, String customerEmail) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId must not be null or blank");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency must not be null or blank");
        }
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.customerEmail = Objects.requireNonNullElse(customerEmail, "");
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
