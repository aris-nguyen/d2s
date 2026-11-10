package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.Map;

public class SubtractExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Map<String, Integer> context) {
        return left.interpret(context) - right.interpret(context);
    }
}
