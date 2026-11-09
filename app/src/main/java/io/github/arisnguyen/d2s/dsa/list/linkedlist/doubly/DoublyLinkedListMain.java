package io.github.arisnguyen.d2s.dsa.list.linkedlist.doubly;

public class DoublyLinkedListMain {
    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("First");
        list.addLast("Second");
        list.addLast("Third");

        System.out.println("List: " + list);
        System.out.println("Size: " + list.size());

        list.remove("Second");
        System.out.println("After remove Second: " + list);
    }
}
