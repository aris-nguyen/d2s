package io.github.arisnguyen.d2s.dsa.queue;

public interface QueueADT<T> extends Iterable<T> {
    void enQueue(T element);
    T deQueue();
    T peek();
    int size();
    boolean isEmpty();
}
