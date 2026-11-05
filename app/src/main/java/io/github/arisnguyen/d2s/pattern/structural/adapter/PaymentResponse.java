package io.github.arisnguyen.d2s.pattern.structural.adapter;

/**
 * Modern payment response representation.
 */
public class PaymentResponse {
    private final String transactionId;
    private final boolean success;
    private final String message;

    public PaymentResponse(String transactionId, boolean success, String message) {
        this.transactionId = transactionId;
        this.success = success;
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
