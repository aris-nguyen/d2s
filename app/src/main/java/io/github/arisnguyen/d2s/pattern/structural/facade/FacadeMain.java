package io.github.arisnguyen.d2s.pattern.structural.facade;

public class FacadeMain {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addStock("PROD-1", 10);

        OrderFulfillmentFacade facade = new OrderFulfillmentFacade(
                inventory,
                new PaymentService(),
                new ShippingService()
        );

        OrderRequest request = new OrderRequest("PROD-1", 2, "ACC-123", 199.99, "123 Main St");
        OrderResult result = facade.placeOrder(request);

        System.out.println("Success: " + result.isSuccess());
        System.out.println("Tracking: " + result.getTrackingNumber());
    }
}
