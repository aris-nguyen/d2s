package io.github.arisnguyen.d2s.pattern.behavioral.iterator;

import java.util.NoSuchElementException;

public class BookShelf implements CustomAggregate<String> {
    private final String[] books;
    private int size = 0;

    public BookShelf(int capacity) {
        this.books = new String[capacity];
    }

    public void addBook(String title) {
        if (size >= books.length) {
            throw new IllegalStateException("BookShelf is full");
        }
        books[size++] = title;
    }

    public int getSize() {
        return size;
    }

    @Override
    public CustomIterator<String> createIterator() {
        return new BookShelfIterator();
    }

    private class BookShelfIterator implements CustomIterator<String> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more books");
            }
            return books[index++];
        }
    }
}
