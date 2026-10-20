package io.github.arisnguyen.d2s.dsa.array;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<T> implements ArrayADT<T>{

    private T[] arr;
    private int capacity;
    private int size = 0;

    public DynamicArray(){
        this(10);
    }

    public DynamicArray(int capacity){
        if(capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative: "+capacity);
        
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    @Override
    public int size(){
        return this.size;
    }

    @Override
    public boolean isEmpty(){
        return this.size == 0;
    }

    @Override
    public T get(int index){
        return this.arr[index];
    }

    @Override
    public void set(int index, T element){
        this.arr[index] = element;
    }

    @Override
    public void clear(){
        for(int i = 0; i < this.size; i++){
            this.arr[i] = null;
        }

        this.size = 0;
    }

    @Override
    public void add(T element){
        if(this.size >= this.capacity) this.grow();

        this.arr[this.size++] = element;
    }

    @Override
    public void removeAt(int removeIndex){
        if(removeIndex < 0 || removeIndex >= this.size) throw new IndexOutOfBoundsException();

        for(int i = removeIndex; i < this.size-1; i++){
            this.arr[i] = this.arr[i+1];
        }
        this.arr[this.size-1] = null;
        this.size--;
    }

    @Override
    public void remove(Object object){
        int removeIndex = this.indexOf(object);
        if(removeIndex < 0) throw new IllegalArgumentException("Object not found");

        this.removeAt(removeIndex);
    }

    @Override
    public int indexOf(Object object) {
        for(int i = 0; i < this.size; i++){
            if(object == null){
                if(this.arr[i] == null) return i;
            } else {
                if(this.arr[i].equals(object)) return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(Object object){
        return this.indexOf(object) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < DynamicArray.this.size;
            }

            @Override
            public T next() {
                if(!hasNext()) throw new NoSuchElementException("No more elements");

                return DynamicArray.this.arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if(isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder(size);

        sb.append("[");
        for(int i = 0; i < this.size; i++){
            if(i > 0) sb.append(", ");
            sb.append(this.arr[i]);
        }
        sb.append("]");

        return sb.toString();
    }

    private void grow(){
        int newCapacity = (this.capacity > 0) ? this.capacity * 2 : 1;
        if(newCapacity < 0 || newCapacity < capacity) throw new OutOfMemoryError("Capacity: " + newCapacity);

        T[] newArr = (T[]) new Object[newCapacity];

        for(int i = 0; i < this.size; i++){
            newArr[i] = arr[i];
            this.arr[i] = null;
        }

        this.capacity = newCapacity;
        this.arr = newArr;
    }
}