package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public interface OrderObserver {
    void onOrderStatusChanged(OrderEvent event);
}
