package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity;

public class PowerOfTwoCapacityPolicy implements CapacityPolicy {
    @Override
    public int adjustCapacity(int capacity) {
        return Integer.highestOneBit(capacity) << 1;
    }
}
