package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class CancelledState implements OrderState {
    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Cannot pay for a cancelled order.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship a cancelled order.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver a cancelled order.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Order is already cancelled.");
    }

    @Override
    public String getStatus() {
        return "CANCELLED";
    }
}
