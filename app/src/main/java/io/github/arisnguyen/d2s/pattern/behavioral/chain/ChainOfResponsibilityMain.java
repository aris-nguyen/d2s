package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class ChainOfResponsibilityMain {
    public static void main(String[] args) {
        OrderValidationHandler pipeline = new AuthenticationHandler();
        pipeline.linkWith(new RoleAuthorizationHandler("CUSTOMER"))
                .linkWith(new RateLimitHandler(60))
                .linkWith(new CartValidationHandler())
                .linkWith(new InventoryCheckHandler());

        System.out.println("=== Testing Valid Order ===");
        OrderContext validOrder = new OrderContext(
                "ORD-1001",
                "Bearer secret-valid-token",
                "CUSTOMER",
                15,
                3,
                10
        );
        try {
            pipeline.validate(validOrder);
            System.out.println("Order " + validOrder.getOrderId() + " passed all validation checks successfully!");
        } catch (ValidationException ex) {
            System.out.println("Validation failed: " + ex.getMessage());
        }

        System.out.println("\n=== Testing Order With Missing Authentication ===");
        OrderContext unauthenticatedOrder = new OrderContext(
                "ORD-1002",
                null,
                "CUSTOMER",
                10,
                1,
                10
        );
        try {
            pipeline.validate(unauthenticatedOrder);
        } catch (ValidationException ex) {
            System.out.println("Validation failed as expected: " + ex.getMessage());
        }

        System.out.println("\n=== Testing Order With Insufficient Inventory ===");
        OrderContext outOfStockOrder = new OrderContext(
                "ORD-1003",
                "Bearer valid-token",
                "CUSTOMER",
                5,
                20,
                5
        );
        try {
            pipeline.validate(outOfStockOrder);
        } catch (ValidationException ex) {
            System.out.println("Validation failed as expected: " + ex.getMessage());
        }
    }
}
