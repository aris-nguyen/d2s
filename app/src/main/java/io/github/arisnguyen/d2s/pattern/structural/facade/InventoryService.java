package io.github.arisnguyen.d2s.pattern.structural.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * Subsystem 1: Manages product stock levels.
 */
public class InventoryService {
    private final Map<String, Integer> stockMap = new HashMap<>();

    public void addStock(String productId, int quantity) {
        if (productId == null || productId.isBlank() || quantity <= 0) {
            return;
        }
        stockMap.put(productId, stockMap.getOrDefault(productId, 0) + quantity);
    }

    public int getStock(String productId) {
        return stockMap.getOrDefault(productId, 0);
    }

    public boolean reserveStock(String productId, int quantity) {
        int current = getStock(productId);
        if (current >= quantity && quantity > 0) {
            stockMap.put(productId, current - quantity);
            return true;
        }
        return false;
    }

    public void releaseStock(String productId, int quantity) {
        if (productId != null && quantity > 0) {
            stockMap.put(productId, getStock(productId) + quantity);
        }
    }
}
