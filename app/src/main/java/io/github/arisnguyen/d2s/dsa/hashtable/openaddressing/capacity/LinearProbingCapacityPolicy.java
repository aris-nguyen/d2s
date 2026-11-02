package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity;

public class LinearProbingCapacityPolicy implements CapacityPolicy {
    private int step;

    public LinearProbingCapacityPolicy(int step) {
        this.step = step;
    }

    @Override
    public int adjustCapacity(int capacity) {
        while(gcd(++capacity, this.step) != 1);
        return capacity;
    }

    private int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }
}
