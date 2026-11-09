package io.github.arisnguyen.d2s.dsa.tree;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);

        System.out.println("Contains 30: " + bst.contains(30));
        System.out.println("Height: " + bst.height());
        System.out.println("Size: " + bst.size());

        bst.remove(30);
        System.out.println("After remove 30, contains: " + bst.contains(30));
    }
}
