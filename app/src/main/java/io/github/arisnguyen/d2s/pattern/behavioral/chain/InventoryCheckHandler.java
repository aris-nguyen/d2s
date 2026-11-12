package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class InventoryCheckHandler extends OrderValidationHandler {
    @Override
    protected void process(OrderContext context) {
        if (context.getAvailableInventory() < context.getItemCount()) {
            throw new ValidationException("Inventory check failed: Insufficient stock (Available: "
                    + context.getAvailableInventory() + ", Requested: " + context.getItemCount() + ")");
        }
    }
}
