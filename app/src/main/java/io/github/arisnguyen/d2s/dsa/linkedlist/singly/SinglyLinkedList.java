package io.github.arisnguyen.d2s.dsa.linkedlist.singly;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import io.github.arisnguyen.d2s.dsa.linkedlist.LinkedListADT;

public class SinglyLinkedList<T> implements LinkedListADT<T> {

    private SinglyNode<T> head;
    private SinglyNode<T> tail;
    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        if (this.isEmpty())
            return;

        SinglyNode<T> current = this.head;
        while (current != null) {
            SinglyNode<T> next = current.getNext();
            current.setData(null);
            current.setNext(null);
            current = next;
        }

        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void addFirst(T data) {
        SinglyNode<T> newNode = new SinglyNode<>(data, this.head);

        if (this.isEmpty()) {
            this.head = this.tail = newNode;
            this.size++;
            return;
        }

        this.head = newNode;
        this.size++;
    }

    @Override
    public void addLast(T data) {
        SinglyNode<T> newNode = new SinglyNode<>(data);

        if (this.isEmpty()) {
            this.head = this.tail = newNode;
            this.size++;
            return;
        }

        this.tail.setNext(newNode);
        this.tail = newNode;
        this.size++;
    }

    @Override
    public void add(int index, T data) {
        if (index < 0 || index > this.size())
            throw new IndexOutOfBoundsException();

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == this.size) {
            addLast(data);
            return;
        }

        SinglyNode<T> prevNode = this.head;
        for (int i = 0; i < index - 1; i++) {
            prevNode = prevNode.getNext();
        }

        SinglyNode<T> newNode = new SinglyNode<>(data, prevNode.getNext());
        prevNode.setNext(newNode);
        this.size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size())
            throw new IndexOutOfBoundsException();

        SinglyNode<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    @Override
    public void set(int index, T data) {
        if (index < 0 || index >= this.size())
            throw new IndexOutOfBoundsException();

        SinglyNode<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        current.setData(data);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if (this.size() == 1) {
            T data = this.head.getData();

            this.head.setData(null);

            this.head = this.tail = null;
            this.size = 0;

            return data;
        }

        SinglyNode<T> oldHead = this.head;

        this.head = oldHead.getNext();
        this.size--;

        T data = oldHead.getData();
        oldHead.setData(null);
        oldHead.setNext(null);

        return data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if (this.size() == 1) {
            T data = this.tail.getData();

            this.tail.setData(null);

            this.head = this.tail = null;
            this.size = 0;

            return data;
        }

        SinglyNode<T> prevTailNode = this.head;
        for (int i = 0; i < this.size - 2; i++) {
            prevTailNode = prevTailNode.getNext();
        }

        T data = this.tail.getData();

        this.tail.setData(null);

        this.tail = prevTailNode;
        this.tail.setNext(null);
        this.size--;

        return data;
    }

    @Override
    public boolean remove(Object object) {
        if (this.head == null) {
            return false;
        }

        if (Objects.equals(this.head.getData(), object)) {
            removeFirst();
            return true;
        }

        SinglyNode<T> prevNode = this.head;
        SinglyNode<T> curNode = prevNode.getNext();

        while (curNode != null) {
            if (Objects.equals(curNode.getData(), object)) {
                prevNode.setNext(curNode.getNext());

                curNode.setData(null);
                curNode.setNext(null);
                this.size--;

                // If current node is the tail node
                if (this.tail == curNode)
                    this.tail = prevNode;

                return true;
            }

            prevNode = curNode;
            curNode = curNode.getNext();
        }

        return false;
    }

    @Override
    public T removeAt(int index) {
        if (this.isEmpty())
            throw new IndexOutOfBoundsException();

        if (index < 0 || index > this.size() - 1)
            throw new IndexOutOfBoundsException();

        if (index == 0)
            return removeFirst();

        if (index == this.size() - 1)
            return removeLast();

        SinglyNode<T> prevNode = this.head;
        SinglyNode<T> curNode = prevNode.getNext();

        for (int i = 1; i < index; i++) {
            prevNode = curNode;
            curNode = curNode.getNext();
        }

        T data = curNode.getData();

        prevNode.setNext(curNode.getNext());

        curNode.setData(null);
        curNode.setNext(null);
        this.size--;

        return data;
    }

    @Override
    public int indexOf(Object object) {
        SinglyNode<T> curNode = this.head;

        int index = 0;
        while (curNode != null) {
            if (Objects.equals(curNode.getData(), object))
                return index;

            curNode = curNode.getNext();
            index++;
        }

        return -1;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private SinglyNode<T> current = SinglyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }

                T data = this.current.getData();
                this.current = this.current.getNext();
                return data;
            }
        };
    }
}
