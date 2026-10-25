package io.github.arisnguyen.d2s.dsa.linkedlist.doubly;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import io.github.arisnguyen.d2s.dsa.linkedlist.LinkedListADT;

public class DoublyLinkedList<T> implements LinkedListADT<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

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
        DoublyNode<T> curNode = this.head;
        while (curNode != null) {
            DoublyNode<T> nextNode = curNode.getNext();
            curNode.setPrev(null);
            curNode.setNext(null);
            curNode.setData(null);
            curNode = nextNode;
        }

        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void addFirst(T data) {
        DoublyNode<T> newNode = new DoublyNode<T>(data, null, this.head);
        if (isEmpty()) {
            this.head = this.tail = newNode;
            this.size++;
            return;
        }
        this.head.setPrev(newNode);
        this.head = newNode;
        this.size++;
    }

    @Override
    public void addLast(T data) {
        DoublyNode<T> newNode = new DoublyNode<>(data, this.tail, null);
        if (isEmpty()) {
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
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException();

        if (index == 0) {
            this.addFirst(data);
            return;
        }

        if (index == this.size) {
            this.addLast(data);
            return;
        }

        DoublyNode<T> prevNode = this.head;
        for (int i = 0; i < index - 1; i++) {
            prevNode = prevNode.getNext();
        }

        DoublyNode<T> newNode = new DoublyNode<T>(data, prevNode, prevNode.getNext());
        prevNode.setNext(newNode);
        prevNode.getNext().setPrev(newNode);
        this.size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException();

        DoublyNode<T> curNode = this.head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.getNext();
        }
        return curNode.getData();
    }

    @Override
    public void set(int index, T data) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException();

        DoublyNode<T> curNode = this.head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.getNext();
        }
        curNode.setData(data);
    }

    @Override
    public T removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        if (this.size == 1) {
            T data = this.head.getData();

            this.head.setData(null);
            this.head.setNext(null);

            this.head = this.tail = null;
            this.size = 0;

            return data;
        }

        DoublyNode<T> removedNode = this.head;

        this.head = this.head.getNext();
        this.size--;

        T data = removedNode.getData();

        removedNode.setData(null);
        removedNode.setNext(null);

        return data;
    }

    @Override
    public T removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        if (this.size == 1) {
            T data = this.tail.getData();

            this.tail.setData(null);
            this.tail.setNext(null);

            this.head = this.tail = null;
            this.size = 0;

            return data;
        }

        DoublyNode<T> removedNode = this.tail;

        this.tail = this.tail.getPrev();
        this.tail.setNext(null);
        this.size--;

        T data = removedNode.getData();

        removedNode.setData(null);
        removedNode.setPrev(null);

        return data;
    }

    @Override
    public boolean remove(Object object) {
        DoublyNode<T> curNode = this.head;
        while (curNode != null) {
            if ((object == null && curNode.getData() == null) || (Objects.equals(curNode.getData(), object))) {
                if (this.head == curNode) {
                    DoublyNode<T> removedNode = this.head;

                    this.head = this.head.getNext();
                    if (this.head != null)
                        this.head.setPrev(null);

                    this.size--;

                    removedNode.setData(null);
                    removedNode.setNext(null);

                    return true;
                }

                if (this.tail == curNode) {
                    DoublyNode<T> removedNode = this.tail;

                    this.tail = this.tail.getPrev();
                    if (this.tail != null)
                        this.tail.setNext(null);

                    this.size--;

                    removedNode.setData(null);
                    removedNode.setPrev(null);

                    return true;
                }

                curNode.getPrev().setNext(curNode.getNext());
                curNode.getNext().setPrev(curNode.getPrev());

                this.size--;

                curNode.setData(null);
                curNode.setPrev(null);
                curNode.setNext(null);

                return true;
            }

            curNode = curNode.getNext();
        }

        return false;
    }

    @Override
    public T removeAt(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException();

        if (index == 0)
            return this.removeFirst();

        if (index == this.size - 1)
            return this.removeLast();

        DoublyNode<T> curNode = this.head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.getNext();
        }

        curNode.getPrev().setNext(curNode.getNext());
        curNode.getNext().setPrev(curNode.getPrev());
        this.size--;

        T data = curNode.getData();
        curNode.setData(null);
        curNode.setPrev(null);
        curNode.setNext(null);

        return data;
    }

    @Override
    public int indexOf(Object object) {
        int index = 0;
        DoublyNode<T> curNode = this.head;
        while (curNode != null) {
            if (object == null && curNode.getData() == null)
                return index;

            if (Objects.equals(curNode.getData(), object))
                return index;

            curNode = curNode.getNext();
            index++;
        }

        return -1;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private DoublyNode<T> curNode = DoublyLinkedList.this.head;

            @Override
            public boolean hasNext() {
                return this.curNode != null;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                T data = this.curNode.getData();
                this.curNode = this.curNode.getNext();
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        DoublyNode<T> curNode = this.head;
        while (curNode != null) {
            sb.append(curNode.getData());

            if (curNode.getNext() != null) {
                sb.append(", ");
            }

            curNode = curNode.getNext();
        }

        sb.append("]");

        return sb.toString();
    }
}
