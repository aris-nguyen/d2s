package io.github.arisnguyen.d2s.dsa.list.linkedlist.singly;

public class SinglyLinkedListMain {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        System.out.println("List: " + list);
        System.out.println("Contains 20: " + list.contains(20));

        list.remove(Integer.valueOf(20));
        System.out.println("After remove 20: " + list);
    }
}
