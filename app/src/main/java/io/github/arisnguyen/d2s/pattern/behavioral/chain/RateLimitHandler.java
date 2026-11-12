package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class RateLimitHandler extends OrderValidationHandler {
    private final int maxRequestsPerMinute;

    public RateLimitHandler(int maxRequestsPerMinute) {
        this.maxRequestsPerMinute = maxRequestsPerMinute;
    }

    @Override
    protected void process(OrderContext context) {
        if (context.getRequestCountPerMinute() > maxRequestsPerMinute) {
            throw new ValidationException("Rate limit exceeded: Too many requests (" + context.getRequestCountPerMinute() + "/min)");
        }
    }
}
