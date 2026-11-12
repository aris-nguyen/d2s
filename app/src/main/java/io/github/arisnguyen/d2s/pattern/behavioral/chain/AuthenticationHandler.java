package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class AuthenticationHandler extends OrderValidationHandler {
    @Override
    protected void process(OrderContext context) {
        if (context.getUserToken() == null || !context.getUserToken().startsWith("Bearer ")) {
            throw new ValidationException("Authentication failed: Missing or invalid token");
        }
    }
}
