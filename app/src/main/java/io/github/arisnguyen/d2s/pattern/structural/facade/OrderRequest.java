package io.github.arisnguyen.d2s.pattern.structural.facade;

/**
 * Data Transfer Object representing an order placement request.
 */
public class OrderRequest {
    private final String productId;
    private final int quantity;
    private final String accountId;
    private final double amount;
    private final String shippingAddress;

    public OrderRequest(String productId, int quantity, String accountId, double amount, String shippingAddress) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("productId must not be null or blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("accountId must not be null or blank");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (shippingAddress == null || shippingAddress.isBlank()) {
            throw new IllegalArgumentException("shippingAddress must not be null or blank");
        }
        this.productId = productId.trim();
        this.quantity = quantity;
        this.accountId = accountId.trim();
        this.amount = amount;
        this.shippingAddress = shippingAddress.trim();
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
