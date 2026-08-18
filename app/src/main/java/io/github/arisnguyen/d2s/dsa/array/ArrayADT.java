package io.github.arisnguyen.d2s.dsa.array;

public interface ArrayADT<T> extends Iterable<T> {
    int size();
    boolean isEmpty();
    T get(int index);
    void set(int index, T element);
    void clear();
    void add(T element);
    void removeAt(int removeIndex);
    void remove(Object object);
    int indexOf(Object object);
    boolean contains(Object object);
}
