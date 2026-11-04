package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing;

public class QuadraticProbe<K> implements ProbingStrategy<K> {
    @Override
    public void setup(Object key, int capacity) {
        //
    }

    @Override
    public int probe(int x) {
        if(x <= 0) return 0;

        return (x * x + x) / 2;
    }
}
