package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.Map;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public int interpret(Map<String, Integer> context) {
        if (!context.containsKey(name)) {
            throw new IllegalArgumentException("Variable not found in context: " + name);
        }
        return context.get(name);
    }
}
