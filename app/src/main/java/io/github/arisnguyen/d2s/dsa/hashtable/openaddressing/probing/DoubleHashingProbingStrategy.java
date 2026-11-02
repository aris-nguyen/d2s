package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing;

public class DoubleHashingProbingStrategy<K> implements ProbingStrategy<K> {
    private int step;

    @Override
    public void setup(Object key, int capacity) {
        this.step = this.calculateStep(key, capacity);
    }

    @Override
    public int probe(int x) {
        if(x <= 0) return 0;
        
        return x * step;
    }
    
    private int calculateStep(Object key, int capacity) {
        int hash = this.hash(key, capacity);
        while(gcd(hash, capacity) != 1){
            hash++;
        }
        return hash;
    }

    private int hash(Object key, int capacity) {
        int hash = (int) (key.hashCode() & 0xFFFFFFFFFL) % capacity;
        return hash;
    }

    private int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }
}
