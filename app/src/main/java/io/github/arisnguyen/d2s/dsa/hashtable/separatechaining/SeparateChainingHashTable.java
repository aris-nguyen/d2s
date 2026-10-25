package io.github.arisnguyen.d2s.dsa.hashtable.separatechaining;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.arisnguyen.d2s.dsa.hashtable.HashTableADT;
import io.github.arisnguyen.d2s.dsa.hashtable.Node;
import io.github.arisnguyen.d2s.dsa.linkedlist.doubly.DoublyLinkedList;

public class SeparateChainingHashTable<K, V> implements HashTableADT<K, V> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;

    private int capacity;
    private final float loadFactor;

    private int size;
    private int threshold;

    private DoublyLinkedList<Node<K, V>>[] table;

    public SeparateChainingHashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public SeparateChainingHashTable(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public SeparateChainingHashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.threshold = (int) (this.capacity * this.loadFactor);
        this.table = new DoublyLinkedList[this.capacity];
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
    public int hashCodeToIndex(int hashedKey) {
        return (int) ((hashedKey & 0xFFFFFFFFFL) % this.capacity);
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = null;
        }
        this.size = 0;
    }

    @Override
    public boolean has(K key) {
        int index = this.hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K, V>> list = this.table[index];

        if (list == null || list.isEmpty())
            return false;

        for (Node<K, V> node : list) {
            if (node.getKey().equals(key))
                return true;
        }
        return false;
    }

    @Override
    public V insert(K key, V value) {
        int index = this.hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K, V>> list = this.table[index];

        if (list == null) {
            list = this.table[index] = new DoublyLinkedList<>();
            return this.addNodeToBucket(list, key, value);
        }

        if (list.isEmpty()) {
            return this.addNodeToBucket(list, key, value);
        }

        for (Node<K, V> node : list) {
            if (node.getKey().equals(key)) {
                V oldValue = node.getValue();
                node.setValue(value);
                return oldValue;
            }
        }
        return this.addNodeToBucket(list, key, value);
    }

    @Override
    public V get(K key) {
        int index = this.hashCodeToIndex(key.hashCode());

        DoublyLinkedList<Node<K, V>> list = this.table[index];
        if (list == null || list.isEmpty())
            return null;

        for (Node<K, V> node : list) {
            if (node.getKey().equals(key))
                return node.getValue();
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int index = this.hashCodeToIndex(key.hashCode());

        DoublyLinkedList<Node<K, V>> list = this.table[index];
        if (list == null || list.isEmpty())
            return null;

        for (Node<K, V> node : list) {
            if (node.getKey().equals(key)) {
                V oldValue = node.getValue();

                list.remove(node);
                this.size--;

                return oldValue;
            }
        }

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        final int elementCount = size();

        return new Iterator<K>() {
            int index = 0;
            Iterator<Node<K, V>> bucketIterator = table[0] == null ? null : table[0].iterator();

            @Override
            public boolean hasNext() {
                if (elementCount != size())
                    throw new ConcurrentModificationException("Table is modified during iteration!");

                // Check bucket is not available
                if (bucketIterator == null || !bucketIterator.hasNext()) {
                    // find next bucket which is available
                    while (true) {
                        // Increase index and check bounds
                        if (++this.index >= SeparateChainingHashTable.this.capacity)
                            return false;

                        // Skip null table
                        if (SeparateChainingHashTable.this.table[this.index] == null)
                            continue;

                        // Skip empty table
                        if (SeparateChainingHashTable.this.table[this.index].isEmpty())
                            continue;

                        bucketIterator = SeparateChainingHashTable.this.table[this.index].iterator();
                        break;
                    }
                }

                return true;
            }

            @Override
            public K next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                return bucketIterator.next().getKey();
            }
        };
    }

    private V addNodeToBucket(DoublyLinkedList<Node<K, V>> list, K key, V value) {
        list.addLast(new Node<K, V>(key, value));
        if (++this.size > this.threshold)
            this.resizeTable();
        return null;
    }

    private void resizeTable() {
        this.capacity *= 2;
        this.threshold = (int) (this.threshold * this.loadFactor);

        DoublyLinkedList<Node<K, V>>[] newTable = new DoublyLinkedList[this.capacity];

        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == null)
                continue;

            for (Node<K, V> node : this.table[i]) {
                int index = hashCodeToIndex(node.getKey().hashCode());

                DoublyLinkedList<Node<K, V>> list = newTable[index];
                if (list == null)
                    newTable[index] = list = new DoublyLinkedList<>();

                list.addLast(node);
            }

            this.table[i].clear();
            this.table[i] = null;
        }

        this.table = newTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] == null)
                continue;

            Iterator<Node<K, V>> bucketIterator = this.table[i].iterator();
            while (bucketIterator.hasNext()) {
                sb.append(bucketIterator.next().toString());

                if (bucketIterator.hasNext())
                    sb.append(", ");
            }
        }

        sb.append("]");

        return sb.toString();
    }
}
