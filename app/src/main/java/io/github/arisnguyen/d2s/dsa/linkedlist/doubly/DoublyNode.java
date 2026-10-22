package io.github.arisnguyen.d2s.dsa.linkedlist.doubly;

import io.github.arisnguyen.d2s.dsa.linkedlist.Node;

public class DoublyNode<T> extends Node<T> {
    private DoublyNode<T> prev;
    private DoublyNode<T> next;

    public DoublyNode(T data) {
        this(data, null, null);
    }

    public DoublyNode(T data, DoublyNode<T> prev, DoublyNode<T> next) {
        super(data);
        this.prev = prev;
        this.next = next;
    }

    public DoublyNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DoublyNode<T> prev) {
        this.prev = prev;
    }

    public DoublyNode<T> getNext() {
        return next;
    }

    public void setNext(DoublyNode<T> next) {
        this.next = next;
    }
}
