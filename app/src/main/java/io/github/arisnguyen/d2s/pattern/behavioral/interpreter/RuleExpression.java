package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

public interface RuleExpression {
    boolean interpret(OrderQueryContext context);
}
