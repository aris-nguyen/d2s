package io.github.arisnguyen.d2s.dsa.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.arisnguyen.d2s.dsa.array.DynamicArray;

public class ArrayBasedStack<T> implements StackADT<T> {
    private final DynamicArray<T> arr;
    private int index = -1;
    
    public ArrayBasedStack() {
        this.arr = new DynamicArray<>();
    }

    @Override
    public void push(T element) {
        this.arr.add(element);
        this.index++;
    }

    @Override
    public T pop() {
        if(this.isEmpty()) throw new NoSuchElementException("Stack is empty");

        T element = this.arr.get(this.index);

        this.arr.removeAt(this.index);
        this.index--;

        return element;
    }

    @Override
    public T top() {
        if(this.isEmpty()) throw new NoSuchElementException("Stack is empty");

        return this.arr.get(this.index);
    }

    @Override
    public int size() {
        return this.index + 1;
    }

    @Override
    public boolean isEmpty() {
        return this.index < 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < ArrayBasedStack.this.size();
            }

            @Override
            public T next() {
                if(!hasNext()) throw new NoSuchElementException("No more elements");

                return ArrayBasedStack.this.arr.get(index++);
            }
        };
    }

    @Override
    public String toString() {
        if(isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder(this.size());

        sb.append("[");
        for(int i = 0; i < this.size(); i++){
            if(i > 0) sb.append(", ");
            sb.append(this.arr.get(i));
        }
        sb.append("]");

        return sb.toString();
    }
}
