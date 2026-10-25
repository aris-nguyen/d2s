package io.github.arisnguyen.d2s.dsa.linkedlist;

import java.util.Iterator;

public interface LinkedListADT<T> extends Iterable<T> {
    int size();

    boolean isEmpty();

    void clear();

    void addFirst(T data);

    void addLast(T data);

    void add(int index, T data);

    T get(int index);

    void set(int index, T data);

    T removeFirst();

    T removeLast();

    boolean remove(Object object);

    T removeAt(int index);

    int indexOf(Object object);

    boolean contains(Object object);

    Iterator<T> iterator();
}
