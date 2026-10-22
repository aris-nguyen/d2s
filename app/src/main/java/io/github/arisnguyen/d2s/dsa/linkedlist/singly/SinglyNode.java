package io.github.arisnguyen.d2s.dsa.linkedlist.singly;

import io.github.arisnguyen.d2s.dsa.linkedlist.Node;

public class SinglyNode<T> extends Node<T> {
    private SinglyNode<T> next;

    public SinglyNode(T data) {
        this(data, null);
    }

    public SinglyNode(T data, SinglyNode<T> next) {
        super(data);
        this.next = next;
    }

    public SinglyNode<T> getNext() {
        return next;
    }

    public void setNext(SinglyNode<T> next) {
        this.next = next;
    }
}
