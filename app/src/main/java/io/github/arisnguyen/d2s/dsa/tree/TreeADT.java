package io.github.arisnguyen.d2s.dsa.tree;

import java.util.Iterator;

public interface TreeADT<T extends Comparable<T>> extends Iterable<T> {
    int size();

    boolean isEmpty();

    void clear();

    int height();

    boolean contains(T element);

    boolean add(T element);

    boolean remove(T element);

    Iterator<T> traverse(TreeTraversalOrder order);

    @Override
    default Iterator<T> iterator() {
        return traverse(TreeTraversalOrder.IN_ORDER);
    }
}
