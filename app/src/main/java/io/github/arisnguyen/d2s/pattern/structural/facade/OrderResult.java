package io.github.arisnguyen.d2s.pattern.structural.facade;

/**
 * Result returned by the Facade encapsulating order processing status.
 */
public class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.success = success;
        this.trackingNumber = trackingNumber;
        this.message = message;
    }

    public static OrderResult success(String trackingNumber, String message) {
        return new OrderResult(true, trackingNumber, message);
    }

    public static OrderResult failure(String message) {
        return new OrderResult(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getMessage() {
        return message;
    }
}
