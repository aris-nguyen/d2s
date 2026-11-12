package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class StateMain {
    public static void main(String[] args) {
        Order order = new Order("ORD-2026-X");
        System.out.println("Initial status: " + order.getStatus());

        order.pay();
        System.out.println("After pay(): " + order.getStatus());

        order.ship();
        System.out.println("After ship(): " + order.getStatus());

        order.deliver();
        System.out.println("After deliver(): " + order.getStatus());

        try {
            order.cancel();
        } catch (IllegalStateException ex) {
            System.out.println("Blocked illegal transition: " + ex.getMessage());
        }
    }
}
