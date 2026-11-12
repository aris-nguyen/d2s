package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

public class OrExpression implements RuleExpression {
    private final RuleExpression left;
    private final RuleExpression right;

    public OrExpression(RuleExpression left, RuleExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(OrderQueryContext context) {
        return left.interpret(context) || right.interpret(context);
    }
}
