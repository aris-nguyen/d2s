package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing;

public class LinearProbe<K> implements ProbingStrategy<K> {
    private static final int LINEAR_CONSTANT = 13;
    
    @Override
    public void setup(Object key, int capacity) {
        //
    }

    @Override
    public int probe(int x) {
        if(x <= 0) return 0;
        
        return x * LINEAR_CONSTANT;
    }
}
