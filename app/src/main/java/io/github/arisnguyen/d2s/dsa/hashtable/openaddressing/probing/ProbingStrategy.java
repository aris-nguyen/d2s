package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing;

public interface ProbingStrategy<K> {
    void setup(K key, int capacity);
    int probe(int x);
}