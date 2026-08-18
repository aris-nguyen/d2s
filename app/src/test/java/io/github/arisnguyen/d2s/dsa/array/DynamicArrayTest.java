package io.github.arisnguyen.d2s.dsa.array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DynamicArrayTest {

    // ---------- Constructors ----------

    @Test
    void defaultConstructorCreatesEmptyArray() {
        DynamicArray<String> arr = new DynamicArray<>();
        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
    }

    @Test
    void constructorWithCapacityCreatesEmptyArray() {
        DynamicArray<String> arr = new DynamicArray<>(3);
        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
    }

    @Test
    void constructorWithNegativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<>(-1));
    }

    // ---------- size / isEmpty ----------

    @Test
    void sizeReflectsNumberOfElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        assertEquals(0, arr.size());

        arr.add("a");
        arr.add("b");
        arr.add("c");
        assertEquals(3, arr.size());
        assertFalse(arr.isEmpty());
    }

    // ---------- add / get / set ----------

    @Test
    void addThenGetReturnsElementsInOrder() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        assertEquals("a", arr.get(0));
        assertEquals("b", arr.get(1));
        assertEquals("c", arr.get(2));
    }

    @Test
    void setOverwritesExistingElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");

        arr.set(1, "z");

        assertEquals("a", arr.get(0));
        assertEquals("z", arr.get(1));
        assertEquals(2, arr.size());
    }

    @Test
    void addBeyondInitialCapacityGrowsAndPreservesOrder() {
        DynamicArray<Integer> arr = new DynamicArray<>(2);

        for (int i = 0; i < 20; i++) {
            arr.add(i);
        }

        assertEquals(20, arr.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, arr.get(i));
        }
    }

    @Test
    void addAllowsNullElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add(null);
        arr.add("a");

        assertEquals(2, arr.size());
        assertEquals(null, arr.get(0));
        assertEquals("a", arr.get(1));
    }

    // ---------- removeAt ----------

    @Test
    void removeAtMiddleShiftsRemainingElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        arr.add("d");

        arr.removeAt(1);

        assertEquals(3, arr.size());
        assertEquals("a", arr.get(0));
        assertEquals("c", arr.get(1));
        assertEquals("d", arr.get(2));
    }

    @Test
    void removeAtFirstElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        arr.removeAt(0);

        assertEquals(2, arr.size());
        assertEquals("b", arr.get(0));
        assertEquals("c", arr.get(1));
    }

    @Test
    void removeAtLastElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        arr.removeAt(2);

        assertEquals(2, arr.size());
        assertEquals("a", arr.get(0));
        assertEquals("b", arr.get(1));
    }

    @Test
    void removeAtOnlyElementLeavesArrayEmpty() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");

        arr.removeAt(0);

        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
    }

    @Test
    void removeAtOutOfBoundsThrows() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");

        assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(2));
    }

    // ---------- remove(Object) ----------

    @Test
    void removeRemovesMatchingElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        arr.remove("b");

        assertEquals(2, arr.size());
        assertEquals("a", arr.get(0));
        assertEquals("c", arr.get(1));
    }

    @Test
    void removeRemovesNullElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add(null);
        arr.add("a");

        arr.remove(null);

        assertEquals(1, arr.size());
        assertEquals("a", arr.get(0));
    }

    @Test
    void removeMissingElementThrows() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");

        assertThrows(IllegalArgumentException.class, () -> arr.remove("not-there"));
    }

    @Test
    void removeFromEmptyArrayThrows() {
        DynamicArray<String> arr = new DynamicArray<>();
        assertThrows(IllegalArgumentException.class, () -> arr.remove("a"));
    }

    // ---------- indexOf ----------

    @Test
    void indexOfFindsElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        assertEquals(0, arr.indexOf("a"));
        assertEquals(1, arr.indexOf("b"));
        assertEquals(2, arr.indexOf("c"));
    }

    @Test
    void indexOfReturnsMinusOneWhenNotFound() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");

        assertEquals(-1, arr.indexOf("z"));
        assertEquals(-1, arr.indexOf(null));
    }

    @Test
    void indexOfHandlesNullElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add(null);
        arr.add("c");

        assertEquals(1, arr.indexOf(null));
    }

    // ---------- contains ----------

    @Test
    void containsReturnsTrueWhenElementIsPresent() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");

        assertTrue(arr.contains("a"));
        assertTrue(arr.contains("b"));
    }

    @Test
    void containsReturnsFalseWhenElementIsAbsent() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");

        assertFalse(arr.contains("z"));
    }

    @Test
    void containsHandlesNullElement() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add(null);

        assertTrue(arr.contains(null));
    }

    // ---------- clear ----------

    @Test
    void clearEmptiesTheArray() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        arr.clear();

        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
    }

    @Test
    void clearOnEmptyArrayIsNoOp() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.clear();
        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
    }

    // ---------- iterator ----------

    @Test
    void iteratorVisitsAllElementsInOrder() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        List<String> collected = new ArrayList<>();
        for (String s : arr) {
            collected.add(s);
        }

        assertEquals(List.of("a", "b", "c"), collected);
    }

    @Test
    void iteratorOverEmptyArrayHasNoElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        assertFalse(arr.iterator().hasNext());
    }

    @Test
    void iteratorReflectsAddsMadeBeforeIteration() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");

        Iterator<String> it = arr.iterator();
        assertTrue(it.hasNext());
        assertEquals("a", it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    // ---------- toString ----------

    @Test
    void toStringOnEmptyArray() {
        assertEquals("[]", new DynamicArray<>().toString());
    }

    @Test
    void toStringFormatsElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");

        assertEquals("[a, b, c]", arr.toString());
    }

    @Test
    void toStringFormatsNullElements() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("a");
        arr.add(null);
        arr.add("c");

        assertEquals("[a, null, c]", arr.toString());
    }

    // ---------- combined behavior ----------

    @Test
    void fullLifecycleMixedOperations() {
        DynamicArray<Integer> arr = new DynamicArray<>(3);

        for (int i = 0; i < 10; i++) {
            arr.add(i);
        }
        assertEquals(10, arr.size());

        arr.removeAt(0);            // remove first
        arr.remove(Integer.valueOf(4)); // remove by value
        arr.set(0, 100);            // overwrite remaining first

        assertEquals(8, arr.size());
        assertEquals(100, arr.get(0));
        assertEquals(-1, arr.indexOf(4));
    }
}
