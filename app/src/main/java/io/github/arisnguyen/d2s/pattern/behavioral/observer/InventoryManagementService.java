package io.github.arisnguyen.d2s.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class InventoryManagementService implements OrderObserver {
    private final List<String> inventoryActions = new ArrayList<>();

    @Override
    public void onOrderStatusChanged(OrderEvent event) {
        if (event.getStatus() == OrderStatus.PAID) {
            inventoryActions.add("Hold stock for order: " + event.getOrderId());
        } else if (event.getStatus() == OrderStatus.CANCELLED) {
            inventoryActions.add("Release stock for cancelled order: " + event.getOrderId());
        }
    }

    public List<String> getInventoryActions() {
        return inventoryActions;
    }
}
