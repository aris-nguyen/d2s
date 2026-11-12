package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

public class GreaterThanExpression implements RuleExpression {
    private final String attributeKey;
    private final double threshold;

    public GreaterThanExpression(String attributeKey, double threshold) {
        this.attributeKey = attributeKey;
        this.threshold = threshold;
    }

    @Override
    public boolean interpret(OrderQueryContext context) {
        Object actual = context.getAttribute(attributeKey);
        if (actual instanceof Number number) {
            return number.doubleValue() > threshold;
        }
        return false;
    }
}
