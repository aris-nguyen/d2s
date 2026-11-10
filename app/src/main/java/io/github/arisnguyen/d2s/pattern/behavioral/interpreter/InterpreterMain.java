package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.HashMap;
import java.util.Map;

public class InterpreterMain {
    public static void main(String[] args) {
        // Expression: (x + 10) - y
        Expression expr = new SubtractExpression(
                new AddExpression(new VariableExpression("x"), new NumberExpression(10)),
                new VariableExpression("y")
        );

        Map<String, Integer> context = new HashMap<>();
        context.put("x", 15);
        context.put("y", 7);

        int result = expr.interpret(context);
        System.out.println("Result of (x + 10) - y where x=15, y=7 is: " + result);
    }
}
