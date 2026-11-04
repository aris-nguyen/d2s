package io.github.arisnguyen.d2s.dsa.hashtable;

public class Node<K, V> {
    private int hash;
    private K key;
    private V value;

    public Node(K key, V value) {
        this.hash = key.hashCode();
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public boolean equals(Node<K, V> other) {
        if (this.hash != other.hash)
            return false;

        return this.key == other.key;
    }

    @Override
    public String toString() {
        return String.format("key=%s,value=%s", this.key, this.value);
    }
}
