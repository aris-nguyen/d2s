package io.github.arisnguyen.d2s.pattern.creational.prototype;

/**
 * Generic Prototype interface for cloning objects.
 *
 * @param <T> Target object type to copy.
 */
public interface Prototype<T> {

    T copy();
}
