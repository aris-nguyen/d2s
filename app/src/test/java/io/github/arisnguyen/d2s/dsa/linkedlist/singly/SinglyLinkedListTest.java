package io.github.arisnguyen.d2s.dsa.linkedlist.singly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class SinglyLinkedListTest {

    @Test
    void newList_isEmptyAndHasSizeZero() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertIterableEquals(List.of(), elementsOf(list));
    }

    @Test
    void addFirst_preservesReverseInsertionOrder() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        assertListState(list, List.of(3, 2, 1));
    }

    @Test
    void addLast_preservesInsertionOrder() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertListState(list, List.of(1, 2, 3));
    }

    @Test
    void add_insertsAtBeginningMiddleAndEnd() {
        SinglyLinkedList<String> list = listOf("B", "D");
        list.add(0, "A");
        list.add(2, "C");
        list.add(list.size(), "E");
        assertListState(list, List.of("A", "B", "C", "D", "E"));
    }

    @Test
    void add_rejectsInvalidIndexes() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));
    }

    @Test
    void getAndSet_workAtBothEndsAndInMiddle() {
        SinglyLinkedList<String> list = listOf("A", "B", "C");
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
        list.set(0, "X");
        list.set(1, "Y");
        list.set(2, "Z");
        assertListState(list, List.of("X", "Y", "Z"));
    }

    @Test
    void getAndSet_rejectInvalidIndexes() {
        SinglyLinkedList<Integer> list = listOf(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, 0));
    }

    @Test
    void removeFirst_removesElementsAndRestoresEmptyState() {
        SinglyLinkedList<Integer> list = listOf(1, 2, 3);
        assertEquals(1, list.removeFirst());
        assertListState(list, List.of(2, 3));
        assertEquals(2, list.removeFirst());
        assertEquals(3, list.removeFirst());
        assertListState(list, List.of());
        assertThrows(NoSuchElementException.class, list::removeFirst);
    }

    @Test
    void removeLast_removesElementsAndKeepsTailUsable() {
        SinglyLinkedList<Integer> list = listOf(1, 2, 3);
        assertEquals(3, list.removeLast());
        assertListState(list, List.of(1, 2));
        assertEquals(2, list.removeLast());
        assertEquals(1, list.removeLast());
        assertListState(list, List.of());
        assertThrows(NoSuchElementException.class, list::removeLast);
        list.addLast(4);
        assertListState(list, List.of(4));
    }

    @Test
    void remove_removesOnlyFirstMatchingElementAndUpdatesTail() {
        SinglyLinkedList<Integer> list = listOf(1, 2, 3, 2);
        assertTrue(list.remove(2));
        assertListState(list, List.of(1, 3, 2));
        assertTrue(list.remove(2));
        assertListState(list, List.of(1, 3));
        assertFalse(list.remove(99));
    }

    @Test
    void remove_supportsNullValues() {
        SinglyLinkedList<String> list = listOf(null, "value", null);
        assertTrue(list.contains(null));
        assertEquals(0, list.indexOf(null));
        assertTrue(list.remove(null));
        assertListState(list, java.util.Arrays.asList("value", null));
        assertTrue(list.remove(null));
        assertFalse(list.contains(null));
    }

    @Test
    void removeAt_removesBeginningMiddleAndEnd() {
        SinglyLinkedList<String> list = listOf("A", "B", "C", "D", "E");
        assertEquals("A", list.removeAt(0));
        assertEquals("C", list.removeAt(1));
        assertEquals("E", list.removeAt(list.size() - 1));
        assertListState(list, List.of("B", "D"));
    }

    @Test
    void removeAt_rejectsInvalidIndexesIncludingEmptyList() {
        SinglyLinkedList<Integer> emptyList = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> list = listOf(1, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> emptyList.removeAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(2));
    }

    @Test
    void containsAndIndexOf_handlePresentAbsentAndDuplicates() {
        SinglyLinkedList<String> list = listOf("A", "B", "A");
        assertTrue(list.contains("A"));
        assertTrue(list.contains("B"));
        assertFalse(list.contains("C"));
        assertEquals(0, list.indexOf("A"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("C"));
    }

    @Test
    void clear_emptiesListAndAllowsReuse() {
        SinglyLinkedList<Integer> list = listOf(1, 2, 3);
        list.clear();
        assertListState(list, List.of());
        list.clear();
        list.addFirst(9);
        list.addLast(10);
        assertListState(list, List.of(9, 10));
    }

    @Test
    void iterator_traversesInOrderAndThrowsAfterExhaustion() {
        Iterator<Integer> iterator = listOf(1, 2, 3).iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void list_acceptsNullDataInAllInsertionPaths() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirst(null);
        list.addLast(null);
        list.add(1, null);
        assertListState(list, java.util.Arrays.asList(null, null, null));
        assertNull(list.get(0));
        assertNull(list.removeFirst());
    }

    private static <T> SinglyLinkedList<T> listOf(T... values) {
        SinglyLinkedList<T> list = new SinglyLinkedList<>();
        for (T value : values)
            list.addLast(value);
        return list;
    }

    private static <T> void assertListState(SinglyLinkedList<T> list, List<T> expected) {
        assertEquals(expected.size(), list.size());
        assertEquals(expected.isEmpty(), list.isEmpty());
        assertIterableEquals(expected, elementsOf(list));
        for (int index = 0; index < expected.size(); index++) {
            assertEquals(expected.get(index), list.get(index));
        }
    }

    private static <T> List<T> elementsOf(SinglyLinkedList<T> list) {
        List<T> elements = new ArrayList<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            elements.add(iterator.next());
        }
        return elements;
    }
}
