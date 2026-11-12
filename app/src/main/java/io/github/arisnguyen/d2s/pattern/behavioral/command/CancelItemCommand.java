package io.github.arisnguyen.d2s.pattern.behavioral.command;

public class CancelItemCommand implements OrderCommand {
    private final OrderService orderService;
    private final String item;
    private final int quantity;

    public CancelItemCommand(OrderService orderService, String item, int quantity) {
        this.orderService = orderService;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        orderService.removeItemFromCart(item, quantity);
    }

    @Override
    public void undo() {
        orderService.addItemToCart(item, quantity);
    }

    @Override
    public String getDescription() {
        return "Cancel " + quantity + " x " + item;
    }
}
