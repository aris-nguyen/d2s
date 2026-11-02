package io.github.arisnguyen.d2s.dsa.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class LinkedListQueueTest {

    // ---------- initial state ----------

    @Test
    void newQueueIsEmpty() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertEquals("[]", queue.toString());
    }

    // ---------- enQueue / peek ----------

    @Test
    void enQueueAddsElementsAndPeekReturnsFirstElement() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("a");
        queue.enQueue("b");
        queue.enQueue("c");

        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals("a", queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void enQueueAllowsNullElements() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue(null);

        assertEquals(1, queue.size());
        assertNull(queue.peek());
    }

    // ---------- deQueue ----------

    @Test
    void deQueueRemovesElementsInFirstInFirstOutOrder() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("a");
        queue.enQueue("b");
        queue.enQueue("c");

        assertEquals("a", queue.deQueue());
        assertEquals(2, queue.size());
        assertEquals("b", queue.peek());

        assertEquals("b", queue.deQueue());
        assertEquals(1, queue.size());
        assertEquals("c", queue.peek());

        assertEquals("c", queue.deQueue());
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void deQueueRemovesNullElement() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue(null);

        assertNull(queue.deQueue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void queueCanBeReusedAfterAllElementsAreDeQueued() {
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        queue.enQueue(1);
        assertEquals(1, queue.deQueue());
        assertTrue(queue.isEmpty());

        queue.enQueue(2);
        queue.enQueue(3);
        assertEquals(2, queue.size());
        assertEquals(2, queue.peek());
        assertEquals(2, queue.deQueue());
        assertEquals(3, queue.deQueue());
        assertTrue(queue.isEmpty());
    }

    // ---------- empty queue ----------

    @Test
    void peekOnEmptyQueueThrows() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();

        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void deQueueOnEmptyQueueThrows() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();

        assertThrows(NoSuchElementException.class, queue::deQueue);
    }

    // ---------- iterator ----------

    @Test
    void iteratorVisitsElementsFromFrontToBack() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("a");
        queue.enQueue("b");
        queue.enQueue("c");

        List<String> collected = new ArrayList<>();
        for (String element : queue) {
            collected.add(element);
        }

        assertEquals(List.of("a", "b", "c"), collected);
    }

    @Test
    void iteratorOverEmptyQueueHasNoElements() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();

        assertFalse(queue.iterator().hasNext());
    }

    @Test
    void iteratorNextAfterLastElementThrows() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("a");
        Iterator<String> iterator = queue.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // ---------- toString ----------

    @Test
    void toStringFormatsElementsFromFrontToBack() {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("a");
        queue.enQueue(null);
        queue.enQueue("c");

        assertEquals("[a, null, c]", queue.toString());
    }

    // ---------- mixed operations ----------

    @Test
    void multipleEnQueueAndDeQueueOperationsMaintainCorrectOrderAndSize() {
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();

        for (int i = 0; i < 50; i++) {
            queue.enQueue(i);
        }
        assertEquals(50, queue.size());

        for (int i = 0; i < 25; i++) {
            assertEquals(i, queue.deQueue());
        }
        assertEquals(25, queue.size());
        assertEquals(25, queue.peek());

        for (int i = 50; i < 100; i++) {
            queue.enQueue(i);
        }
        assertEquals(75, queue.size());

        for (int i = 25; i < 100; i++) {
            assertEquals(i, queue.deQueue());
        }
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
}
