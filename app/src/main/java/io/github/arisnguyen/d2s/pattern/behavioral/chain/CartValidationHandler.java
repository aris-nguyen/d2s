package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class CartValidationHandler extends OrderValidationHandler {
    @Override
    protected void process(OrderContext context) {
        if (context.getItemCount() <= 0) {
            throw new ValidationException("Cart validation failed: Order cart cannot be empty");
        }
    }
}
