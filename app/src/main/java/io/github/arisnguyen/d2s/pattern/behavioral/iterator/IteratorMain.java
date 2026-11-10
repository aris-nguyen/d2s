package io.github.arisnguyen.d2s.pattern.behavioral.iterator;

public class IteratorMain {
    public static void main(String[] args) {
        BookShelf shelf = new BookShelf(3);
        shelf.addBook("Design Patterns: Elements of Reusable Object-Oriented Software");
        shelf.addBook("Clean Code");
        shelf.addBook("Refactoring");

        CustomIterator<String> iterator = shelf.createIterator();
        System.out.println("Books in shelf:");
        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }
    }
}
