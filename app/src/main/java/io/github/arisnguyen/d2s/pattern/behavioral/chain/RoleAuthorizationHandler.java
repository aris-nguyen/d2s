package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class RoleAuthorizationHandler extends OrderValidationHandler {
    private final String requiredRole;

    public RoleAuthorizationHandler(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    @Override
    protected void process(OrderContext context) {
        if (!requiredRole.equalsIgnoreCase(context.getUserRole())) {
            throw new ValidationException("Authorization failed: Role " + context.getUserRole() + " is not permitted to place orders");
        }
    }
}
