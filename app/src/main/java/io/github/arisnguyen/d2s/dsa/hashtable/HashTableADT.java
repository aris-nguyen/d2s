package io.github.arisnguyen.d2s.dsa.hashtable;

import java.util.Iterator;

public interface HashTableADT<K, V> {
    int size();

    boolean isEmpty();

    int hashCodeToIndex(int hashedKey);

    void clear();

    boolean has(K key);

    V insert(K key, V value);

    V get(K key);

    V remove(K key);

    Iterator<K> iterator();
}
