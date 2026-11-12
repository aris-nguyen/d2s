package io.github.arisnguyen.d2s.pattern.behavioral.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateTest {

    @Test
    void testHappyOrderLifecycle() {
        Order order = new Order("ORD-HAPPY");
        assertEquals("CREATED", order.getStatus());
        assertTrue(order.getState() instanceof CreatedState);

        order.pay();
        assertEquals("PAID", order.getStatus());
        assertTrue(order.getState() instanceof PaidState);

        order.ship();
        assertEquals("SHIPPED", order.getStatus());
        assertTrue(order.getState() instanceof ShippedState);

        order.deliver();
        assertEquals("DELIVERED", order.getStatus());
        assertTrue(order.getState() instanceof DeliveredState);
    }

    @Test
    void testOrderCancellationLifecycle() {
        Order order = new Order("ORD-CANCEL");
        order.pay();
        assertEquals("PAID", order.getStatus());

        order.cancel();
        assertEquals("CANCELLED", order.getStatus());
        assertTrue(order.getState() instanceof CancelledState);

        assertThrows(IllegalStateException.class, order::pay);
        assertThrows(IllegalStateException.class, order::ship);
    }

    @Test
    void testInvalidTransitions() {
        Order order = new Order("ORD-INVALID");

        // Cannot ship before pay
        assertThrows(IllegalStateException.class, order::ship);

        order.pay();
        order.ship();

        // Cannot cancel once shipped
        assertThrows(IllegalStateException.class, order::cancel);
    }
}
