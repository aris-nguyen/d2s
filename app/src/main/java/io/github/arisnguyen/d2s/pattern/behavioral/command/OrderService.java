package io.github.arisnguyen.d2s.pattern.behavioral.command;

import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private final Map<String, Integer> cart = new HashMap<>();
    private final Map<String, Integer> inventory = new HashMap<>();

    public OrderService() {
        inventory.put("LAPTOP", 10);
        inventory.put("PHONE", 20);
        inventory.put("KEYBOARD", 30);
    }

    public void addItemToCart(String item, int quantity) {
        int currentStock = inventory.getOrDefault(item, 0);
        if (currentStock < quantity) {
            throw new IllegalStateException("Insufficient inventory for " + item);
        }
        inventory.put(item, currentStock - quantity);
        cart.put(item, cart.getOrDefault(item, 0) + quantity);
    }

    public void removeItemFromCart(String item, int quantity) {
        int currentCartQty = cart.getOrDefault(item, 0);
        if (currentCartQty < quantity) {
            throw new IllegalStateException("Cannot remove more items than in cart");
        }
        cart.put(item, currentCartQty - quantity);
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }

    public int getCartQuantity(String item) {
        return cart.getOrDefault(item, 0);
    }

    public int getInventoryStock(String item) {
        return inventory.getOrDefault(item, 0);
    }

    public Map<String, Integer> getCart() {
        return new HashMap<>(cart);
    }
}
