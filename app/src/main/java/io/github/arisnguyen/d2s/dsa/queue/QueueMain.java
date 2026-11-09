package io.github.arisnguyen.d2s.dsa.queue;

public class QueueMain {
    public static void main(String[] args) {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
        queue.enQueue("Request 1");
        queue.enQueue("Request 2");
        queue.enQueue("Request 3");

        System.out.println("Peek: " + queue.peek());
        System.out.println("DeQueue: " + queue.deQueue());
        System.out.println("Remaining size: " + queue.size());
    }
}
