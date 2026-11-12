package io.github.arisnguyen.d2s.pattern.behavioral.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverTest {

    private OrderSubject orderManager;
    private EmailNotificationService emailService;
    private InventoryManagementService inventoryService;

    @BeforeEach
    void setUp() {
        orderManager = new OrderSubject();
        emailService = new EmailNotificationService();
        inventoryService = new InventoryManagementService();

        orderManager.attach(emailService);
        orderManager.attach(inventoryService);
    }

    @Test
    void testOrderPaidNotifiesBothServices() {
        OrderEvent paidEvent = new OrderEvent("ORD-1", OrderStatus.PAID, "test@domain.com", 100.0);
        orderManager.notifyObservers(paidEvent);

        assertEquals(1, emailService.getSentEmails().size());
        assertTrue(emailService.getSentEmails().get(0).contains("ORD-1 status is now PAID"));

        assertEquals(1, inventoryService.getInventoryActions().size());
        assertTrue(inventoryService.getInventoryActions().get(0).contains("Hold stock for order: ORD-1"));
    }

    @Test
    void testDetachObserverStopsReceivingEvents() {
        orderManager.detach(inventoryService);

        OrderEvent cancelledEvent = new OrderEvent("ORD-2", OrderStatus.CANCELLED, "test@domain.com", 50.0);
        orderManager.notifyObservers(cancelledEvent);

        assertEquals(1, emailService.getSentEmails().size());
        assertTrue(inventoryService.getInventoryActions().isEmpty());
    }
}
