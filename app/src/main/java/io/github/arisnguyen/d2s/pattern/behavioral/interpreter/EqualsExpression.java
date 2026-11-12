package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

public class EqualsExpression implements RuleExpression {
    private final String attributeKey;
    private final Object expectedValue;

    public EqualsExpression(String attributeKey, Object expectedValue) {
        this.attributeKey = attributeKey;
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean interpret(OrderQueryContext context) {
        Object actual = context.getAttribute(attributeKey);
        return expectedValue == null ? actual == null : expectedValue.equals(actual);
    }
}
