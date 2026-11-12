package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public class ObserverMain {
    public static void main(String[] args) {
        OrderSubject orderManager = new OrderSubject();

        EmailNotificationService emailService = new EmailNotificationService();
        InventoryManagementService inventoryService = new InventoryManagementService();

        orderManager.attach(emailService);
        orderManager.attach(inventoryService);

        System.out.println("=== Simulating Order Paid Event ===");
        OrderEvent paidEvent = new OrderEvent("ORD-2026-001", OrderStatus.PAID, "customer@example.com", 299.99);
        orderManager.notifyObservers(paidEvent);

        System.out.println("Emails sent: " + emailService.getSentEmails());
        System.out.println("Inventory actions: " + inventoryService.getInventoryActions());

        System.out.println("\n=== Simulating Order Cancelled Event ===");
        OrderEvent cancelledEvent = new OrderEvent("ORD-2026-002", OrderStatus.CANCELLED, "vip@example.com", 150.00);
        orderManager.notifyObservers(cancelledEvent);

        System.out.println("Emails sent: " + emailService.getSentEmails());
        System.out.println("Inventory actions: " + inventoryService.getInventoryActions());
    }
}
