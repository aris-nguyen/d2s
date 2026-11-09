package io.github.arisnguyen.d2s.dsa.list.arraylist;

public class ArrayListMain {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");

        System.out.println("List: " + list);
        System.out.println("Get at index 1: " + list.get(1));

        list.remove("Banana");
        System.out.println("After remove Banana: " + list);
    }
}
