package io.github.arisnguyen.d2s.dsa.stack;

public class StackMain {
    public static void main(String[] args) {
        ArrayBasedStack<String> stack = new ArrayBasedStack<>();
        stack.push("Task 1");
        stack.push("Task 2");
        stack.push("Task 3");

        System.out.println("Top: " + stack.top());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Remaining size: " + stack.size());
    }
}
