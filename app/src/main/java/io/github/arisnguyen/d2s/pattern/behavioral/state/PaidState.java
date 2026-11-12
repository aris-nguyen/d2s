package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class PaidState implements OrderState {
    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already paid.");
    }

    @Override
    public void ship(Order order) {
        order.setState(new ShippedState());
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver order before shipping.");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "PAID";
    }
}
