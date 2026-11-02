package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing;

import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity.LinearProbingCapacityPolicy;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity.PowerOfTwoCapacityPolicy;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity.PrimeCapacityPolicy;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing.DoubleHashingProbingStrategy;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing.LinearProbe;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing.QuadraticProbe;

public class OpenAddressingHashTableFactory {
    public static <K, V> OpenAddressingHashTable.Builder<K, V> createLinear(int step) {
        return new OpenAddressingHashTable.Builder<K, V>(
            new LinearProbe<K>(),
            new LinearProbingCapacityPolicy(step)
        );
    }

    public static <K, V> OpenAddressingHashTable.Builder<K, V> createQuadratic() {
        return new OpenAddressingHashTable.Builder<K, V>(
            new QuadraticProbe<K>(),
            new PowerOfTwoCapacityPolicy()
        );
    }

    public static <K, V> OpenAddressingHashTable.Builder<K, V> createDoubleHashing() {
        return new OpenAddressingHashTable.Builder<K, V>(
            new DoubleHashingProbingStrategy<K>(),
            new PrimeCapacityPolicy()
        );
    }
}
