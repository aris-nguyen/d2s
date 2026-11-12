package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public abstract class OrderValidationHandler {
    private OrderValidationHandler nextHandler;

    public OrderValidationHandler linkWith(OrderValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public void validate(OrderContext context) {
        process(context);
        if (nextHandler != null) {
            nextHandler.validate(context);
        }
    }

    protected abstract void process(OrderContext context);
}
