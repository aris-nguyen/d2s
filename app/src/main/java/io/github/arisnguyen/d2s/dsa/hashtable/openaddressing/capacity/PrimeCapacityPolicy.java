package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity;

public class PrimeCapacityPolicy implements CapacityPolicy {
    @Override
    public int adjustCapacity(int capacity) {
        while(!isPrime(++capacity));
        return capacity;
    }

    private boolean isPrime(int capacity) {
        if(capacity < 2) return false;

        if(capacity == 2) return true;

        for(int i = 2; i <= Math.sqrt(capacity); i++) {
            if(capacity % i == 0) return false;
        }

        return true;
    }
}
