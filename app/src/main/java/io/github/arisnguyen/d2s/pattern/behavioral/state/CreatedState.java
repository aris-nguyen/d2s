package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class CreatedState implements OrderState {
    @Override
    public void pay(Order order) {
        order.setState(new PaidState());
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship order before payment.");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver unpaid order.");
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

    @Override
    public String getStatus() {
        return "CREATED";
    }
}
