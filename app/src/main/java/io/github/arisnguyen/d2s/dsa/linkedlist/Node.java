package io.github.arisnguyen.d2s.dsa.linkedlist;

import java.util.Objects;

public class Node<T> {
    private T data;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Node<?>))
            return false;

        Node<?> other = (Node<?>) obj;

        return Objects.equals(this.data, other.data);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
