package io.github.arisnguyen.d2s.pattern.behavioral.command;

public class CommandMain {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        OrderCommandInvoker invoker = new OrderCommandInvoker();

        System.out.println("=== Adding Items ===");
        OrderCommand addLaptops = new AddItemCommand(orderService, "LAPTOP", 2);
        OrderCommand addPhones = new AddItemCommand(orderService, "PHONE", 3);

        invoker.executeCommand(addLaptops);
        invoker.executeCommand(addPhones);

        System.out.println("Cart: " + orderService.getCart());
        System.out.println("Remaining Laptops in inventory: " + orderService.getInventoryStock("LAPTOP"));

        System.out.println("\n=== Undo Last Action ===");
        invoker.undoLastCommand(); // Undo adding phones
        System.out.println("Cart after undo: " + orderService.getCart());
        System.out.println("Remaining Phones in inventory: " + orderService.getInventoryStock("PHONE"));

        System.out.println("\n=== Audit Log History ===");
        invoker.getAuditLogs().forEach(System.out::println);
    }
}
