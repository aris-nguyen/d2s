package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

class DoubleHashingHashTableTest {

    @Test
    void newTableIsEmpty() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }

    @Test
    void emptyTableReturnsNullForMissingEntries() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        assertFalse(table.has("missing"));
        assertNull(table.get("missing"));
        assertNull(table.remove("missing"));
    }

    @Test
    void nullKeyThrowsException() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        assertThrows(IllegalArgumentException.class, () -> table.insert(null, "value"));
        assertThrows(IllegalArgumentException.class, () -> table.get(null));
        assertThrows(IllegalArgumentException.class, () -> table.has(null));
        assertThrows(IllegalArgumentException.class, () -> table.remove(null));
    }

    @Test
    void insertNewKeyStoresValueAndReturnsNull() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        assertNull(table.insert("a", "1"));
        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
        assertTrue(table.has("a"));
        assertEquals("1", table.get("a"));
    }

    @Test
    void insertMultipleKeysStoresEachEntry() {
        OpenAddressingHashTable<Integer, String> table = OpenAddressingHashTableFactory.<Integer, String>createDoubleHashing()
                .capacity(17)
                .loadFactor(0.6f)
                .build();

        for (int i = 0; i < 7; i++) {
            assertNull(table.insert(i, "val-" + i));
        }

        assertEquals(7, table.size());
        for (int i = 0; i < 7; i++) {
            assertTrue(table.has(i));
            assertEquals("val-" + i, table.get(i));
        }
    }

    @Test
    void insertExistingKeyReplacesValueAndKeepsSize() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        table.insert("a", "1");
        assertEquals("1", table.insert("a", "2"));
        assertEquals(1, table.size());
        assertEquals("2", table.get("a"));
    }

    @Test
    void insertWithEqualButDistinctKeyReplacesValue() {
        OpenAddressingHashTable<Key, String> table = OpenAddressingHashTableFactory.<Key, String>createDoubleHashing()
                .build();

        Key first = new Key("alpha");
        Key second = new Key("alpha");

        table.insert(first, "1");
        assertEquals("1", table.insert(second, "2"));
        assertEquals(1, table.size());
        assertEquals("2", table.get(first));
        assertEquals("2", table.get(second));
    }

    @Test
    void keysWithNegativeHashCodesAreSupported() {
        OpenAddressingHashTable<Integer, String> table = OpenAddressingHashTableFactory.<Integer, String>createDoubleHashing()
                .capacity(17)
                .build();

        table.insert(-10, "neg10");
        table.insert(-42, "neg42");

        assertEquals(2, table.size());
        assertEquals("neg10", table.get(-10));
        assertEquals("neg42", table.get(-42));
        assertTrue(table.has(-10));
        assertTrue(table.has(-42));
    }

    @Test
    void doubleHashingResolvesCollisions() {
        OpenAddressingHashTable<CollidingKey, String> table = OpenAddressingHashTableFactory.<CollidingKey, String>createDoubleHashing()
                .capacity(17)
                .loadFactor(0.7f)
                .build();

        CollidingKey k1 = new CollidingKey("k1");
        CollidingKey k2 = new CollidingKey("k2");
        CollidingKey k3 = new CollidingKey("k3");

        table.insert(k1, "v1");
        table.insert(k2, "v2");
        table.insert(k3, "v3");

        assertEquals(3, table.size());
        assertEquals("v1", table.get(k1));
        assertEquals("v2", table.get(k2));
        assertEquals("v3", table.get(k3));
        assertTrue(table.has(k1));
        assertTrue(table.has(k2));
        assertTrue(table.has(k3));
    }

    @Test
    void removeReturnsValueAndDecrementsSize() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .build();

        table.insert("a", "1");
        assertEquals("1", table.remove("a"));
        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
        assertFalse(table.has("a"));
        assertNull(table.get("a"));
    }

    @Test
    void tombstonePreservesLookupChainForSubsequentCollisions() {
        OpenAddressingHashTable<CollidingKey, String> table = OpenAddressingHashTableFactory.<CollidingKey, String>createDoubleHashing()
                .capacity(17)
                .loadFactor(0.7f)
                .build();

        CollidingKey k1 = new CollidingKey("k1");
        CollidingKey k2 = new CollidingKey("k2");
        CollidingKey k3 = new CollidingKey("k3");

        table.insert(k1, "v1");
        table.insert(k2, "v2");
        table.insert(k3, "v3");

        assertEquals("v2", table.remove(k2));

        assertFalse(table.has(k2));
        assertNull(table.get(k2));
        assertEquals("v1", table.get(k1));
        assertEquals("v3", table.get(k3));
    }

    @Test
    void tombstoneSlotIsReusedOnSubsequentInsert() {
        OpenAddressingHashTable<CollidingKey, String> table = OpenAddressingHashTableFactory.<CollidingKey, String>createDoubleHashing()
                .capacity(17)
                .loadFactor(0.7f)
                .build();

        CollidingKey k1 = new CollidingKey("k1");
        CollidingKey k2 = new CollidingKey("k2");

        table.insert(k1, "v1");
        table.insert(k2, "v2");

        table.remove(k1);

        CollidingKey k3 = new CollidingKey("k3");
        assertNull(table.insert(k3, "v3"));

        assertEquals(2, table.size());
        assertEquals("v3", table.get(k3));
        assertEquals("v2", table.get(k2));
    }

    @Test
    void clearRemovesAllEntriesAndResetsState() {
        OpenAddressingHashTable<Integer, String> table = OpenAddressingHashTableFactory.<Integer, String>createDoubleHashing()
                .build();

        for (int i = 0; i < 3; i++) {
            table.insert(i, "v" + i);
        }

        table.clear();
        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
        for (int i = 0; i < 3; i++) {
            assertFalse(table.has(i));
            assertNull(table.get(i));
        }
        assertFalse(table.iterator().hasNext());
    }

    @Test
    void keysAndValuesListsReturnAllActiveEntries() {
        OpenAddressingHashTable<String, Integer> table = OpenAddressingHashTableFactory.<String, Integer>createDoubleHashing()
                .build();

        table.insert("a", 1);
        table.insert("b", 2);
        table.insert("c", 3);
        table.remove("b");

        List<String> keys = table.keys();
        List<Integer> values = table.values();

        assertEquals(2, keys.size());
        assertEquals(2, values.size());
        assertEquals(Set.of("a", "c"), new HashSet<>(keys));
        assertEquals(Set.of(1, 3), new HashSet<>(values));
    }

    @Test
    void tableResizesWithPrimeCapacityPolicyWithoutDataLoss() {
        OpenAddressingHashTable<Integer, String> table = OpenAddressingHashTableFactory.<Integer, String>createDoubleHashing()
                .capacity(7)
                .loadFactor(0.5f)
                .build();

        for (int i = 0; i < 12; i++) {
            table.insert(i, "val-" + i);
        }

        assertEquals(12, table.size());
        for (int i = 0; i < 12; i++) {
            assertTrue(table.has(i));
            assertEquals("val-" + i, table.get(i));
        }
    }

    @Test
    void iteratorTraversesAllKeysAndSkipsTombstones() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .capacity(17)
                .build();

        table.insert("k1", "v1");
        table.insert("k2", "v2");
        table.insert("k3", "v3");
        table.remove("k2");

        List<String> visited = new ArrayList<>();
        Iterator<String> it = table.iterator();
        while (it.hasNext()) {
            visited.add(it.next());
        }

        assertEquals(2, visited.size());
        assertEquals(Set.of("k1", "k3"), new HashSet<>(visited));
    }

    @Test
    void iteratorThrowsNoSuchElementExceptionWhenExhausted() {
        OpenAddressingHashTable<String, String> table = OpenAddressingHashTableFactory.<String, String>createDoubleHashing()
                .capacity(7)
                .build();

        table.insert("k1", "v1");

        Iterator<String> it = table.iterator();
        assertTrue(it.hasNext());
        assertEquals("k1", it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    private static final class Key {
        private final String value;

        Key(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Key key && Objects.equals(this.value, key.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }
    }

    private static final class CollidingKey {
        private final String value;

        CollidingKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof CollidingKey key && Objects.equals(this.value, key.value);
        }

        @Override
        public int hashCode() {
            return 34;
        }
    }
}
