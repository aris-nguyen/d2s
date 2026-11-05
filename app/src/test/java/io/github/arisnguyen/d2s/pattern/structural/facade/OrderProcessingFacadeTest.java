package io.github.arisnguyen.d2s.pattern.structural.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Facade Pattern Tests - Order Fulfillment Subsystem")
class OrderProcessingFacadeTest {

    private InventoryService inventoryService;
    private PaymentService paymentService;
    private ShippingService shippingService;
    private OrderFulfillmentFacade facade;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        paymentService = new PaymentService();
        shippingService = new ShippingService();
        facade = new OrderFulfillmentFacade(inventoryService, paymentService, shippingService);

        inventoryService.addStock("PROD-1", 10);
    }

    @Test
    @DisplayName("Should successfully place order when stock and payment are valid")
    void testPlaceOrderSuccess() {
        OrderRequest request = new OrderRequest("PROD-1", 2, "ACC-123", 199.99, "123 Main St, Springfield");
        OrderResult result = facade.placeOrder(request);

        assertTrue(result.isSuccess());
        assertNotNull(result.getTrackingNumber());
        assertTrue(result.getTrackingNumber().startsWith("TRACK-"));
        assertEquals(8, inventoryService.getStock("PROD-1"));
    }

    @Test
    @DisplayName("Should fail order when inventory is insufficient")
    void testPlaceOrderInsufficientInventory() {
        OrderRequest request = new OrderRequest("PROD-1", 20, "ACC-123", 199.99, "123 Main St, Springfield");
        OrderResult result = facade.placeOrder(request);

        assertFalse(result.isSuccess());
        assertNull(result.getTrackingNumber());
        assertTrue(result.getMessage().contains("Insufficient inventory"));
        assertEquals(10, inventoryService.getStock("PROD-1"));
    }

    @Test
    @DisplayName("Should fail order and roll back reserved inventory when payment fails")
    void testPlaceOrderPaymentFailureRollsBackStock() {
        paymentService.blockAccount("ACC-BLOCKED");

        OrderRequest request = new OrderRequest("PROD-1", 3, "ACC-BLOCKED", 150.00, "456 Elm St, Gotham");
        OrderResult result = facade.placeOrder(request);

        assertFalse(result.isSuccess());
        assertNull(result.getTrackingNumber());
        assertTrue(result.getMessage().contains("Payment failed"));
        // Stock must be restored back to 10
        assertEquals(10, inventoryService.getStock("PROD-1"));
    }

    @Test
    @DisplayName("Should throw exception for null request or null subsystem dependencies")
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () -> facade.placeOrder(null));
        assertThrows(NullPointerException.class, () ->
            new OrderFulfillmentFacade(null, paymentService, shippingService)
        );
    }
}
