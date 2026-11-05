package io.github.arisnguyen.d2s.pattern.structural.facade;

import java.util.Objects;

/**
 * Facade: Provides a simple unified interface for the client to execute
 * the entire order fulfillment workflow, orchestrating Inventory, Payment, and Shipping subsystems.
 */
public class OrderFulfillmentFacade {
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;

    public OrderFulfillmentFacade(InventoryService inventoryService,
                                  PaymentService paymentService,
                                  ShippingService shippingService) {
        this.inventoryService = Objects.requireNonNull(inventoryService, "inventoryService must not be null");
        this.paymentService = Objects.requireNonNull(paymentService, "paymentService must not be null");
        this.shippingService = Objects.requireNonNull(shippingService, "shippingService must not be null");
    }

    public OrderResult placeOrder(OrderRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        // Step 1: Inventory Reservation
        boolean stockReserved = inventoryService.reserveStock(request.getProductId(), request.getQuantity());
        if (!stockReserved) {
            return OrderResult.failure("Insufficient inventory for product " + request.getProductId());
        }

        // Step 2: Payment Processing
        boolean paymentSuccessful = paymentService.processPayment(request.getAccountId(), request.getAmount());
        if (!paymentSuccessful) {
            // Compensating transaction: roll back reserved stock
            inventoryService.releaseStock(request.getProductId(), request.getQuantity());
            return OrderResult.failure("Payment failed for account " + request.getAccountId());
        }

        // Step 3: Shipping
        String trackingNumber = shippingService.shipProduct(
            request.getProductId(),
            request.getQuantity(),
            request.getShippingAddress()
        );

        return OrderResult.success(trackingNumber, "Order fulfilled successfully");
    }
}
