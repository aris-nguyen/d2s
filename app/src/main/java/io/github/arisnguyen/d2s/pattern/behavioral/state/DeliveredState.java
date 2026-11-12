package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class DeliveredState implements OrderState {
    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Order is already delivered.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel a completed delivery.");
    }

    @Override
    public String getStatus() {
        return "DELIVERED";
    }
}
