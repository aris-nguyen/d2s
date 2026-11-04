package io.github.arisnguyen.d2s.dsa.list.arraylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ArrayListTest {

    @Test
    void defaultConstructorCreatesEmptyArray() {
        ArrayList<String> arr = new ArrayList<>();
        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
        assertEquals("[]", arr.toString());
        assertArrayState(arr, List.of());
    }

    @Test
    void constructorWithCapacityCreatesEmptyArray() {
        ArrayList<String> arr = new ArrayList<>(3);
        assertTrue(arr.isEmpty());
        assertEquals(0, arr.size());
        assertArrayState(arr, List.of());
    }

    @Test
    void constructorWithNegativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(-1));
    }

    @Test
    void emptyArray_throwsOnRemoveFirstAndRemoveLast() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertThrows(NoSuchElementException.class, arr::removeFirst);
        assertThrows(NoSuchElementException.class, arr::removeLast);
    }

    @Test
    void add_appendsToTail() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("A");
        arr.add("B");
        assertArrayState(arr, List.of("A", "B"));
    }

    @Test
    void addFirst_preservesReverseInsertionOrder() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.addFirst(1);
        arr.addFirst(2);
        arr.addFirst(3);
        assertArrayState(arr, List.of(3, 2, 1));
    }

    @Test
    void addLast_preservesInsertionOrder() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.addLast(1);
        arr.addLast(2);
        arr.addLast(3);
        assertArrayState(arr, List.of(1, 2, 3));
    }

    @Test
    void add_insertsAtBeginningMiddleAndEnd() {
        ArrayList<String> arr = arrayOf("B", "D");
        arr.add(0, "A");
        arr.add(2, "C");
        arr.add(arr.size(), "E");
        assertArrayState(arr, List.of("A", "B", "C", "D", "E"));
    }

    @Test
    void add_rejectsInvalidIndexes() {
        ArrayList<Integer> arr = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.add(1, 1));
    }

    @Test
    void add_acceptsNullDataInAllInsertionPaths() {
        ArrayList<String> arr = new ArrayList<>();
        arr.addFirst(null);
        arr.addLast(null);
        arr.add(1, null);
        assertArrayState(arr, Arrays.asList(null, null, null));
        assertNull(arr.get(0));
        assertNull(arr.removeFirst());
    }

    @Test
    void getAndSet_workAtBothEndsAndInMiddle() {
        ArrayList<String> arr = arrayOf("A", "B", "C");
        assertEquals("A", arr.get(0));
        assertEquals("B", arr.get(1));
        assertEquals("C", arr.get(2));

        arr.set(0, "X");
        arr.set(1, "Y");
        arr.set(2, "Z");
        assertArrayState(arr, List.of("X", "Y", "Z"));
    }

    @Test
    void getAndSet_rejectInvalidIndexes() {
        ArrayList<Integer> empty = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> empty.set(0, 1));

        ArrayList<Integer> arr = arrayOf(1);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.set(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.set(1, 0));
    }

    @Test
    void removeFirst_removesElementsAndRestoresEmptyState() {
        ArrayList<Integer> arr = arrayOf(1, 2, 3);
        assertEquals(1, arr.removeFirst());
        assertArrayState(arr, List.of(2, 3));
        assertEquals(2, arr.removeFirst());
        assertEquals(3, arr.removeFirst());
        assertArrayState(arr, List.of());
        assertThrows(NoSuchElementException.class, arr::removeFirst);

        arr.addFirst(10);
        assertArrayState(arr, List.of(10));
    }

    @Test
    void removeLast_removesElementsAndRestoresEmptyState() {
        ArrayList<Integer> arr = arrayOf(1, 2, 3);
        assertEquals(3, arr.removeLast());
        assertArrayState(arr, List.of(1, 2));
        assertEquals(2, arr.removeLast());
        assertEquals(1, arr.removeLast());
        assertArrayState(arr, List.of());
        assertThrows(NoSuchElementException.class, arr::removeLast);

        arr.addLast(4);
        assertArrayState(arr, List.of(4));
    }

    @Test
    void removeAt_removesBeginningMiddleAndEnd() {
        ArrayList<String> arr = arrayOf("A", "B", "C", "D", "E");
        assertEquals("A", arr.removeAt(0));
        assertEquals("C", arr.removeAt(1));
        assertEquals("E", arr.removeAt(arr.size() - 1));
        assertArrayState(arr, List.of("B", "D"));
    }

    @Test
    void removeAt_rejectsInvalidIndexesIncludingEmptyArray() {
        ArrayList<Integer> empty = new ArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.removeAt(0));

        ArrayList<Integer> arr = arrayOf(1, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.removeAt(2));
    }

    @Test
    void remove_removesOnlyFirstMatchingElement() {
        ArrayList<Integer> arr = arrayOf(1, 2, 3, 2);
        assertTrue(arr.remove(Integer.valueOf(2)));
        assertArrayState(arr, List.of(1, 3, 2));
        assertTrue(arr.remove(Integer.valueOf(2)));
        assertArrayState(arr, List.of(1, 3));
        assertFalse(arr.remove(Integer.valueOf(99)));
    }

    @Test
    void remove_singleElementRestoresEmptyStateAndAllowsFurtherAdd() {
        ArrayList<Integer> arr = arrayOf(42);
        assertTrue(arr.remove(Integer.valueOf(42)));
        assertArrayState(arr, List.of());

        arr.addLast(100);
        arr.addFirst(50);
        assertArrayState(arr, List.of(50, 100));
    }

    @Test
    void remove_supportsNullValues() {
        ArrayList<String> arr = arrayOf(null, "value", null);
        assertTrue(arr.contains(null));
        assertEquals(0, arr.indexOf(null));
        assertTrue(arr.remove(null));
        assertArrayState(arr, Arrays.asList("value", null));
        assertTrue(arr.remove(null));
        assertFalse(arr.contains(null));
    }

    @Test
    void containsAndIndexOf_handlePresentAbsentAndDuplicates() {
        ArrayList<String> arr = arrayOf("A", "B", "A");
        assertTrue(arr.contains("A"));
        assertTrue(arr.contains("B"));
        assertFalse(arr.contains("C"));
        assertEquals(0, arr.indexOf("A"));
        assertEquals(1, arr.indexOf("B"));
        assertEquals(-1, arr.indexOf("C"));
    }

    @Test
    void clear_emptiesArrayAndAllowsReuse() {
        ArrayList<Integer> arr = arrayOf(1, 2, 3);
        arr.clear();
        assertArrayState(arr, List.of());

        arr.addFirst(9);
        arr.addLast(10);
        assertArrayState(arr, List.of(9, 10));
    }

    @Test
    void addBeyondInitialCapacityGrowsAndPreservesOrder() {
        ArrayList<Integer> arr = new ArrayList<>(2);

        for (int i = 0; i < 20; i++) {
            arr.addLast(i);
        }

        assertEquals(20, arr.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, arr.get(i));
        }
    }

    @Test
    void iterator_traversesInOrderAndThrowsAfterExhaustion() {
        Iterator<Integer> iterator = arrayOf(1, 2, 3).iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iterator_onEmptyArrayHasNoNext() {
        Iterator<Integer> iterator = new ArrayList<Integer>().iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void toString_formatsEmptyAndPopulatedArrays() {
        ArrayList<String> arr = new ArrayList<>();
        assertEquals("[]", arr.toString());

        arr.addLast("A");
        arr.addLast("B");
        assertEquals("[A, B]", arr.toString());
    }

    @SafeVarargs
    private static <T> ArrayList<T> arrayOf(T... values) {
        ArrayList<T> arr = new ArrayList<>(Math.max(values.length, 1));
        for (T value : values) {
            arr.addLast(value);
        }
        return arr;
    }

    private static <T> void assertArrayState(ArrayList<T> arr, List<T> expected) {
        assertEquals(expected.size(), arr.size());
        assertEquals(expected.isEmpty(), arr.isEmpty());
        assertIterableEquals(expected, elementsOf(arr));
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), arr.get(i));
        }
    }

    private static <T> List<T> elementsOf(ArrayList<T> arr) {
        List<T> list = new java.util.ArrayList<>();
        for (T item : arr) {
            list.add(item);
        }
        return list;
    }
}
