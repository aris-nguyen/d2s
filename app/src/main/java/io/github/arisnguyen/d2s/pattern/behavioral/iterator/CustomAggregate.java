package io.github.arisnguyen.d2s.pattern.behavioral.iterator;

public interface CustomAggregate<T> {
    CustomIterator<T> createIterator();
}
