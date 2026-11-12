package io.github.arisnguyen.d2s.pattern.behavioral.command;

public class AddItemCommand implements OrderCommand {
    private final OrderService orderService;
    private final String item;
    private final int quantity;

    public AddItemCommand(OrderService orderService, String item, int quantity) {
        this.orderService = orderService;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        orderService.addItemToCart(item, quantity);
    }

    @Override
    public void undo() {
        orderService.removeItemFromCart(item, quantity);
    }

    @Override
    public String getDescription() {
        return "Add " + quantity + " x " + item;
    }
}
