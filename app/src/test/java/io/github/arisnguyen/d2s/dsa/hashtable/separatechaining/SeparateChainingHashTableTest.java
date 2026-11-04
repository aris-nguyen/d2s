package io.github.arisnguyen.d2s.dsa.hashtable.separatechaining;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SeparateChainingHashTableTest {

    private static final int DEFAULT_CAPACITY = 10;

    // ---------- initial state ----------

    @Test
    void newTableIsEmpty() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();

        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }

    @Test
    void emptyTableReturnsNullForMissingEntries() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();

        assertFalse(table.has("missing"));
        assertNull(table.get("missing"));
        assertNull(table.remove("missing"));
    }

    @Test
    void iteratorOverEmptyTableHasNoElements() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();

        assertFalse(table.iterator().hasNext());
    }

    // ---------- insert / get / has ----------

    @Test
    void insertNewKeyStoresValueAndReturnsNull() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();

        assertNull(table.insert("a", "1"));

        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
        assertTrue(table.has("a"));
        assertEquals("1", table.get("a"));
    }

    @Test
    void insertMultipleKeysStoresEachEntry() {
        SeparateChainingHashTable<Integer, String> table = new SeparateChainingHashTable<>();
        for (int i = 0; i < 5; i++) {
            assertNull(table.insert(i, "value-" + i));
        }

        assertEquals(5, table.size());
        for (int i = 0; i < 5; i++) {
            assertTrue(table.has(i));
            assertEquals("value-" + i, table.get(i));
        }
    }

    @Test
    void insertExistingKeyReplacesValueAndKeepsSize() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        assertEquals("1", table.insert("a", "2"));

        assertEquals(1, table.size());
        assertEquals("2", table.get("a"));
    }

    @Test
    void insertWithEqualButDistinctKeyReplacesValue() {
        SeparateChainingHashTable<Key, String> table = new SeparateChainingHashTable<>();
        Key stored = new Key("alpha");
        table.insert(stored, "first");

        assertEquals("first", table.insert(new Key("alpha"), "second"));
        assertEquals(1, table.size());
        assertEquals("second", table.get(stored));
    }

    @Test
    void getReturnsNullForMissingKey() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        assertNull(table.get("b"));
    }

    @Test
    void getFindsValueForEqualButDistinctKey() {
        SeparateChainingHashTable<Key, String> table = new SeparateChainingHashTable<>();
        table.insert(new Key("alpha"), "value");

        assertEquals("value", table.get(new Key("alpha")));
    }

    // ---------- collisions ----------

    @Test
    void collidingKeysAreStoredIndependently() {
        SeparateChainingHashTable<CollidingKey, String> table = new SeparateChainingHashTable<>();
        CollidingKey first = new CollidingKey("first");
        CollidingKey second = new CollidingKey("second");

        table.insert(first, "1");
        table.insert(second, "2");

        assertEquals(2, table.size());
        assertTrue(table.has(first));
        assertTrue(table.has(second));
        assertEquals("1", table.get(first));
        assertEquals("2", table.get(second));
        assertEquals(Set.of(first, second), keysOf(table));
    }

    @Test
    void removeFromCollisionBucketKeepsOtherEntries() {
        SeparateChainingHashTable<CollidingKey, String> table = new SeparateChainingHashTable<>();
        CollidingKey first = new CollidingKey("first");
        CollidingKey second = new CollidingKey("second");
        table.insert(first, "1");
        table.insert(second, "2");

        assertEquals("1", table.remove(first));

        assertEquals(1, table.size());
        assertFalse(table.has(first));
        assertEquals("2", table.get(second));
    }

    // ---------- remove ----------

    @Test
    void removeReturnsValueAndDecrementsSize() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        assertEquals("1", table.remove("a"));

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
        assertFalse(table.has("a"));
        assertNull(table.get("a"));
    }

    @Test
    void removeMissingKeyReturnsNullAndKeepsSize() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        assertNull(table.remove("b"));

        assertEquals(1, table.size());
        assertEquals("1", table.get("a"));
    }

    @Test
    void removeUsesKeyEquality() {
        SeparateChainingHashTable<Key, String> table = new SeparateChainingHashTable<>();
        table.insert(new Key("alpha"), "value");

        assertEquals("value", table.remove(new Key("alpha")));
        assertTrue(table.isEmpty());
    }

    @Test
    void tableCanBeReusedAfterRemovingAllEntries() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");
        table.remove("a");

        assertNull(table.insert("a", "2"));

        assertEquals(1, table.size());
        assertEquals("2", table.get("a"));
    }

    // ---------- clear ----------

    @Test
    void clearRemovesAllEntries() {
        SeparateChainingHashTable<Integer, String> table = new SeparateChainingHashTable<>();
        for (int i = 1; i <= 3; i++) {
            table.insert(i, "value-" + i);
        }

        table.clear();

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
        for (int i = 1; i <= 3; i++) {
            assertFalse(table.has(i));
            assertNull(table.get(i));
        }
        assertFalse(table.iterator().hasNext());
    }

    @Test
    void tableCanBeReusedAfterClear() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");
        table.insert("b", "2");
        table.clear();

        assertNull(table.insert("a", "3"));

        assertEquals(1, table.size());
        assertEquals("3", table.get("a"));
    }

    // ---------- hashing ----------

    @Test
    void hashCodeToIndexIsWithinCapacity() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        int[] hashedKeys = { 0, 1, -1, 42, -42, Integer.MIN_VALUE, Integer.MAX_VALUE, 123456789, -987654321 };

        for (int hashedKey : hashedKeys) {
            int index = table.hashCodeToIndex(hashedKey);

            assertTrue(index >= 0 && index < DEFAULT_CAPACITY,
                    "index " + index + " out of range for hash " + hashedKey);
        }
    }

    @Test
    void hashCodeToIndexRespectsCustomCapacity() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>(7);
        int[] hashedKeys = { 0, 1, -1, 42, Integer.MIN_VALUE, Integer.MAX_VALUE };

        for (int hashedKey : hashedKeys) {
            int index = table.hashCodeToIndex(hashedKey);

            assertTrue(index >= 0 && index < 7,
                    "index " + index + " out of range for hash " + hashedKey);
        }
    }

    @Test
    void hashCodeToIndexIsDeterministic() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>(7);

        assertEquals(table.hashCodeToIndex(42), table.hashCodeToIndex(42));
        assertEquals(table.hashCodeToIndex(-1), table.hashCodeToIndex(-1));
        assertEquals(table.hashCodeToIndex(Integer.MIN_VALUE), table.hashCodeToIndex(Integer.MIN_VALUE));
    }

    // ---------- resizing ----------

    @Test
    void insertBeyondThresholdGrowsTableWithoutLosingEntries() {
        SeparateChainingHashTable<Integer, String> table = new SeparateChainingHashTable<>();

        // The default threshold is 5, so the sixth insertion must grow the table.
        for (int i = 0; i < 6; i++) {
            table.insert(i, "value-" + i);
        }

        assertEquals(6, table.size());
        for (int i = 0; i < 6; i++) {
            assertTrue(table.has(i));
            assertEquals("value-" + i, table.get(i));
        }
    }

    @Test
    void insertingManyEntriesKeepsAllEntriesRetrievable() {
        SeparateChainingHashTable<Integer, String> table = new SeparateChainingHashTable<>();

        for (int i = 0; i < 16; i++) {
            table.insert(i, "value-" + i);
        }

        assertEquals(16, table.size());
        for (int i = 0; i < 16; i++) {
            assertEquals("value-" + i, table.get(i));
        }
    }

    // ---------- iterator ----------

    @Test
    void iteratorVisitsEveryKeyExactlyOnce() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        for (String key : List.of("a", "b", "c", "d", "e")) {
            table.insert(key, key.toUpperCase());
        }

        List<String> visited = new ArrayList<>();
        Iterator<String> iterator = table.iterator();
        while (iterator.hasNext()) {
            visited.add(iterator.next());
        }

        assertEquals(5, visited.size());
        assertEquals(Set.of("a", "b", "c", "d", "e"), new HashSet<>(visited));
    }

    @Test
    void iteratorReflectsRemovals() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        for (String key : List.of("a", "b", "c")) {
            table.insert(key, key);
        }
        table.remove("b");

        assertEquals(Set.of("a", "c"), keysOf(table));
    }

    @Test
    void iteratorNextReturnsFirstElementWithoutHasNext() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        assertEquals("a", table.iterator().next());
    }

    @Test
    void iteratorNextAfterLastElementThrows() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        table.insert("a", "1");

        Iterator<String> iterator = table.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorFailsFastWhenTableIsModified() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        for (String key : List.of("a", "b", "c")) {
            table.insert(key, key);
        }

        Iterator<String> iterator = table.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        table.insert("d", "d");

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    @Test
    void iteratorFailsFastWhenEntryIsRemoved() {
        SeparateChainingHashTable<String, String> table = new SeparateChainingHashTable<>();
        for (String key : List.of("a", "b", "c")) {
            table.insert(key, key);
        }

        Iterator<String> iterator = table.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        table.remove("b");

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
    }

    // ---------- helpers ----------

    private static <K> Set<K> keysOf(SeparateChainingHashTable<K, ?> table) {
        Set<K> keys = new HashSet<>();
        Iterator<K> iterator = table.iterator();
        while (iterator.hasNext()) {
            K key = iterator.next();
            assertTrue(keys.add(key), "duplicate key returned by iterator: " + key);
        }
        return keys;
    }

    /** Key with value-based equality, used to verify equals-based lookups. */
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

        @Override
        public String toString() {
            return "Key[" + this.value + "]";
        }
    }

    /** Key whose hash never varies, used to force bucket collisions. */
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
            return 42;
        }

        @Override
        public String toString() {
            return "CollidingKey[" + this.value + "]";
        }
    }
}
