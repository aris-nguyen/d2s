package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterpreterTest {

    @Test
    void testNumberExpression() {
        Expression expr = new NumberExpression(42);
        assertEquals(42, expr.interpret(Map.of()));
    }

    @Test
    void testVariableExpression() {
        Expression expr = new VariableExpression("val");
        assertEquals(100, expr.interpret(Map.of("val", 100)));
    }

    @Test
    void testVariableNotFound() {
        Expression expr = new VariableExpression("missing");
        assertThrows(IllegalArgumentException.class, () -> expr.interpret(Map.of()));
    }

    @Test
    void testAddAndSubtractExpression() {
        // (a + b) - c -> (20 + 30) - 15 = 35
        Expression expr = new SubtractExpression(
                new AddExpression(new VariableExpression("a"), new VariableExpression("b")),
                new VariableExpression("c")
        );

        Map<String, Integer> context = new HashMap<>();
        context.put("a", 20);
        context.put("b", 30);
        context.put("c", 15);

        assertEquals(35, expr.interpret(context));
    }
}
