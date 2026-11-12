package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public class OrderEvent {
    private final String orderId;
    private final OrderStatus status;
    private final String customerEmail;
    private final double totalAmount;

    public OrderEvent(String orderId, OrderStatus status, String customerEmail, double totalAmount) {
        this.orderId = orderId;
        this.status = status;
        this.customerEmail = customerEmail;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
