package io.github.arisnguyen.d2s.pattern.behavioral.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainOfResponsibilityTest {

    private OrderValidationHandler pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new AuthenticationHandler();
        pipeline.linkWith(new RoleAuthorizationHandler("CUSTOMER"))
                .linkWith(new RateLimitHandler(60))
                .linkWith(new CartValidationHandler())
                .linkWith(new InventoryCheckHandler());
    }

    @Test
    void testValidOrderPassesAllChecks() {
        OrderContext validOrder = new OrderContext(
                "ORD-OK",
                "Bearer token123",
                "CUSTOMER",
                10,
                2,
                5
        );
        assertDoesNotThrow(() -> pipeline.validate(validOrder));
    }

    @Test
    void testMissingAuthenticationFailsFirst() {
        OrderContext unauthOrder = new OrderContext(
                "ORD-FAIL",
                "invalid-token-format",
                "CUSTOMER",
                5,
                1,
                10
        );
        ValidationException ex = assertThrows(ValidationException.class, () -> pipeline.validate(unauthOrder));
        assertTrue(ex.getMessage().contains("Authentication failed"));
    }

    @Test
    void testRoleAuthorizationFails() {
        OrderContext guestOrder = new OrderContext(
                "ORD-GUEST",
                "Bearer token123",
                "GUEST",
                5,
                1,
                10
        );
        ValidationException ex = assertThrows(ValidationException.class, () -> pipeline.validate(guestOrder));
        assertTrue(ex.getMessage().contains("Authorization failed"));
    }

    @Test
    void testRateLimitFails() {
        OrderContext floodedOrder = new OrderContext(
                "ORD-FLOOD",
                "Bearer token123",
                "CUSTOMER",
                120, // exceeds 60
                1,
                10
        );
        ValidationException ex = assertThrows(ValidationException.class, () -> pipeline.validate(floodedOrder));
        assertTrue(ex.getMessage().contains("Rate limit exceeded"));
    }

    @Test
    void testEmptyCartFails() {
        OrderContext emptyOrder = new OrderContext(
                "ORD-EMPTY",
                "Bearer token123",
                "CUSTOMER",
                10,
                0, // empty cart
                10
        );
        ValidationException ex = assertThrows(ValidationException.class, () -> pipeline.validate(emptyOrder));
        assertTrue(ex.getMessage().contains("Cart validation failed"));
    }

    @Test
    void testInsufficientInventoryFails() {
        OrderContext outOfStockOrder = new OrderContext(
                "ORD-OOS",
                "Bearer token123",
                "CUSTOMER",
                10,
                10, // wants 10
                2   // only 2 available
        );
        ValidationException ex = assertThrows(ValidationException.class, () -> pipeline.validate(outOfStockOrder));
        assertTrue(ex.getMessage().contains("Inventory check failed"));
    }
}
