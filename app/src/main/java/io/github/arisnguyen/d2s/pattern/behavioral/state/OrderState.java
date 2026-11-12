package io.github.arisnguyen.d2s.pattern.behavioral.state;

public interface OrderState {
    void pay(Order order);
    void ship(Order order);
    void deliver(Order order);
    void cancel(Order order);
    String getStatus();
}
