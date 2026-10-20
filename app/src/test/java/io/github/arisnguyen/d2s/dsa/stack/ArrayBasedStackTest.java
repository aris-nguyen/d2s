package io.github.arisnguyen.d2s.dsa.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayBasedStackTest {

    // ---------- initial state ----------

    @Test
    void newStackIsEmpty() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertEquals("[]", stack.toString());
    }

    // ---------- push / top ----------

    @Test
    void pushAddsElementsAndTopReturnsMostRecentElement() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");

        assertEquals(3, stack.size());
        assertFalse(stack.isEmpty());
        assertEquals("c", stack.top());
        assertEquals(3, stack.size());
    }

    @Test
    void pushAllowsNullElements() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push(null);

        assertEquals(1, stack.size());
        assertNull(stack.top());
    }

    // ---------- pop ----------

    @Test
    void popRemovesElementsInLastInFirstOutOrder() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");

        assertEquals("c", stack.pop());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.pop());
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void popRemovesNullElement() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push(null);

        assertNull(stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void stackCanBeReusedAfterAllElementsArePopped() {
        ArrayBasedStack<Integer> stack = new ArrayBasedStack<>();
        stack.push(1);
        stack.pop();
        stack.push(2);

        assertEquals(2, stack.top());
        assertEquals(2, stack.pop());
        assertTrue(stack.isEmpty());
    }

    // ---------- empty stack ----------

    @Test
    void topOnEmptyStackThrows() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();

        assertThrows(NoSuchElementException.class, stack::top);
    }

    @Test
    void popOnEmptyStackThrows() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();

        assertThrows(NoSuchElementException.class, stack::pop);
    }

    // ---------- iterator ----------

    @Test
    void iteratorVisitsElementsFromBottomToTop() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");

        List<String> collected = new ArrayList<>();
        for (String element : stack) {
            collected.add(element);
        }

        assertEquals(List.of("a", "b", "c"), collected);
    }

    @Test
    void iteratorOverEmptyStackHasNoElements() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();

        assertFalse(stack.iterator().hasNext());
    }

    @Test
    void iteratorNextAfterLastElementThrows() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("a");
        Iterator<String> iterator = stack.iterator();

        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    // ---------- toString ----------

    @Test
    void toStringFormatsElementsFromBottomToTop() {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("a");
        stack.push(null);
        stack.push("c");

        assertEquals("[a, null, c]", stack.toString());
    }
}
