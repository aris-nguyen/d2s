package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import io.github.arisnguyen.d2s.dsa.hashtable.HashTableADT;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.capacity.CapacityPolicy;
import io.github.arisnguyen.d2s.dsa.hashtable.openaddressing.probing.ProbingStrategy;

public class OpenAddressingHashTable<K, V> implements HashTableADT<K, V> {
    private ProbingStrategy<K> probe;
    private CapacityPolicy capacityPolicy;

    private static final int DEFAULT_CAPACITY = 7;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;

    private int capacity;
    private final float loadFactor;

    private int size;
    private int used;
    private int threshold;

    protected final K TOMBSTONE = (K) (new Object());

    protected K[] keys;
    protected V[] values;

    private OpenAddressingHashTable() {
        this(DEFAULT_CAPACITY);
    }

    private OpenAddressingHashTable(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    private OpenAddressingHashTable(int capacity, float loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.used = 0;
        this.size = 0;
        this.threshold = (int) (this.capacity * this.loadFactor);

        this.keys = (K[]) new Object[this.capacity];
        this.values = (V[]) new Object[this.capacity];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int hashCodeToIndex(int hashedKey) {
        return (int) ((hashedKey & 0xFFFFFFFFFL) % capacity);
    }

    public void clear() {
        for (int i = 0; i < this.capacity; i++) {
            this.keys[i] = null;
            this.values[i] = null;
        }
        this.size = this.used = 0;
    }

    public boolean has(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        this.probe.setup(key, this.capacity);

        int x = 0;
        int firstTombstoneIndex = -1;
        while (true) {
            final int offset = key.hashCode();
            final int probedOffset = this.probe.probe(x++);
            final int index = this.hashCodeToIndex(offset + probedOffset);

            if (this.keys[index] == TOMBSTONE) {
                if (firstTombstoneIndex == -1)
                    firstTombstoneIndex = index;
                continue;
            }

            if (this.keys[index] != null) {
                if (this.keys[index].equals(key))
                    return true;
                continue;
            }

            return false;
        }
    }

    public V insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        if (this.used >= this.threshold)
            this.resizeTable();

        this.probe.setup(key, this.capacity);

        int x = 0;
        int firstTombstoneIndex = -1;
        while (true) {
            final int offset = key.hashCode();
            final int probedOffset = this.probe.probe(x++);
            final int index = this.hashCodeToIndex(offset + probedOffset);

            if (this.keys[index] == TOMBSTONE) {
                if (firstTombstoneIndex == -1)
                    firstTombstoneIndex = index;
                continue;
            }

            if (this.keys[index] != null) {
                if (this.keys[index].equals(key)) {
                    V oldValue = this.values[index];

                    if (firstTombstoneIndex == -1) {
                        this.values[index] = value;
                        return oldValue;
                    } else {
                        this.keys[firstTombstoneIndex] = key;
                        this.values[firstTombstoneIndex] = value;
                        this.keys[index] = TOMBSTONE;
                        this.values[index] = null;
                        return oldValue;
                    }
                }
                continue;
            }

            if (firstTombstoneIndex == -1) {
                this.keys[index] = key;
                this.values[index] = value;
                this.size++;
                this.used++;
                return null;
            } else {
                this.size++;
                this.keys[firstTombstoneIndex] = key;
                this.values[firstTombstoneIndex] = value;
                return null;
            }
        }
    }

    public V get(K key) {
        if(key == null)
            throw new IllegalArgumentException("Key cannot be null");

        this.probe.setup(key, this.capacity);

        int x = 0;
        int firstTombstoneIndex = -1;
        while (true) {
            final int offset = key.hashCode();
            final int probedOffset = this.probe.probe(x++);
            final int index = this.hashCodeToIndex(offset + probedOffset);

            if(this.keys[index] == TOMBSTONE){
                if(firstTombstoneIndex == -1)
                    firstTombstoneIndex = index;
                continue;
            }
            
            if(this.keys[index] != null){
                if(this.keys[index].equals(key))
                    return this.values[index];
                continue;
            }

            return null;
        }
    }

    public V remove(K key) {
        if(key == null)
            throw new IllegalArgumentException("Key cannot be null");

        this.probe.setup(key, this.capacity);

        int x = 0;
        while (true) {
            final int offset = key.hashCode();
            final int probedOffset = this.probe.probe(x++);
            final int index = this.hashCodeToIndex(offset + probedOffset);

            if(this.keys[index] == TOMBSTONE) continue;
            if(this.keys[index] == null) return null;

            if(this.keys[index].equals(key)){
                V oldValue = this.values[index];
                this.keys[index] = TOMBSTONE;
                this.values[index] = null;
                this.size--;
                return oldValue;
            }
        }
    }

    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int index = 0;
            private int nextIndex = -1;

            @Override
            public boolean hasNext() {
                if (nextIndex != -1) {
                    return true;
                }
                while (index < OpenAddressingHashTable.this.capacity) {
                    if (OpenAddressingHashTable.this.keys[index] != null &&
                        OpenAddressingHashTable.this.keys[index] != TOMBSTONE) {
                        nextIndex = index;
                        return true;
                    }
                    index++;
                }
                return false;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                K key = OpenAddressingHashTable.this.keys[nextIndex];
                index = nextIndex + 1;
                nextIndex = -1;
                return key;
            }
        };
    }

    private void setProbe(ProbingStrategy<K> probe) {
        this.probe = probe;
    }

    private void setCapacityPolicy(CapacityPolicy capacityPolicy) {
        this.capacityPolicy = capacityPolicy;
    }

    public List<K> keys() {
        List<K> keys = new ArrayList<>(this.size);
        for (int i = 0; i < this.capacity; i++) {
            if (this.keys[i] == null)
                continue;
            if (this.keys[i] == TOMBSTONE)
                continue;

            keys.add(this.keys[i]);
        }
        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>(this.size);
        for (int i = 0; i < this.capacity; i++) {
            if (this.values[i] == null)
                continue;
            if (this.values[i] == TOMBSTONE)
                continue;

            values.add(this.values[i]);
        }
        return values;
    }

    private void resizeTable() {
        this.capacity = this.capacityPolicy.adjustCapacity(this.capacity);
        this.threshold = (int) (this.capacity * this.loadFactor);

        // Swap keys
        K[] tempKeys = this.keys;
        this.keys = (K[]) new Object[this.capacity];

        // Swap values
        V[] tempValues = this.values;
        this.values = (V[]) new Object[this.capacity];

        // Reset used and size
        this.size = this.used = 0;

        // Re-insert all keys and values
        for (int i = 0; i < tempKeys.length; i++) {
            if (tempKeys[i] == null)
                continue;
            if (tempKeys[i] == TOMBSTONE)
                continue;

            this.insert(tempKeys[i], tempValues[i]);
            tempKeys[i] = null;
            tempValues[i] = null;
        }

        // Nullify temp keys and values
        tempKeys = null;
        tempValues = null;
    }

    public static class Builder<K, V> {
        private ProbingStrategy<K> probe;
        private CapacityPolicy capacityPolicy;

        private int capacity = DEFAULT_CAPACITY;
        private float loadFactor = DEFAULT_LOAD_FACTOR;

        public Builder(ProbingStrategy<K> probe, CapacityPolicy capacityPolicy) {
            this.probe = probe;
            this.capacityPolicy = capacityPolicy;
        }

        public Builder<K, V> capacity(int capacity) {
            if (capacity <= 0)
                throw new IllegalArgumentException("Capacity must be greater than 0");

            this.capacity = capacity;
            return this;
        }

        public Builder<K, V> loadFactor(float loadFactor) {
            if (loadFactor <= 0)
                throw new IllegalArgumentException("Load factor must be greater than 0");

            this.loadFactor = loadFactor;
            return this;
        }

        public OpenAddressingHashTable<K, V> build() {
            OpenAddressingHashTable<K, V> hashTable = new OpenAddressingHashTable<>(this.capacity, this.loadFactor);
            hashTable.setProbe(this.probe);
            hashTable.setCapacityPolicy(this.capacityPolicy);
            return hashTable;
        }
    }
}
