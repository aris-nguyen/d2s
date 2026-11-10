package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.Map;

public interface Expression {
    int interpret(Map<String, Integer> context);
}
