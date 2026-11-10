package io.github.arisnguyen.d2s.pattern.behavioral.iterator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IteratorTest {

    @Test
    void testIteratingElements() {
        BookShelf shelf = new BookShelf(3);
        shelf.addBook("Book A");
        shelf.addBook("Book B");

        CustomIterator<String> iterator = shelf.createIterator();
        List<String> items = new ArrayList<>();
        while (iterator.hasNext()) {
            items.add(iterator.next());
        }

        assertEquals(List.of("Book A", "Book B"), items);
    }

    @Test
    void testEmptyIterator() {
        BookShelf shelf = new BookShelf(2);
        CustomIterator<String> iterator = shelf.createIterator();

        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testOverflow() {
        BookShelf shelf = new BookShelf(1);
        shelf.addBook("Book 1");
        assertThrows(IllegalStateException.class, () -> shelf.addBook("Book 2"));
    }
}
