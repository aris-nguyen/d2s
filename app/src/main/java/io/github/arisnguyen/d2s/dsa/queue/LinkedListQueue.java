package io.github.arisnguyen.d2s.dsa.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.arisnguyen.d2s.dsa.list.linkedlist.LinkedListADT;
import io.github.arisnguyen.d2s.dsa.list.linkedlist.doubly.DoublyLinkedList;

public class LinkedListQueue<T> implements QueueADT<T> {

    private LinkedListADT<T> list;

    public LinkedListQueue() {
        this.list = new DoublyLinkedList<T>();
    }

    @Override
    public void enQueue(T element) {
        this.list.addLast(element);
    }

    @Override
    public T deQueue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return this.list.removeFirst();
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return this.list.get(0);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
