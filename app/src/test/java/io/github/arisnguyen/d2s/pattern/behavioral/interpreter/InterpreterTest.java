package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InterpreterTest {

    @Test
    void testEqualsExpression() {
        RuleExpression expr = new EqualsExpression("status", "PAID");
        assertTrue(expr.interpret(new OrderQueryContext(Map.of("status", "PAID"))));
        assertFalse(expr.interpret(new OrderQueryContext(Map.of("status", "PENDING"))));
        assertFalse(expr.interpret(new OrderQueryContext(Map.of())));
    }

    @Test
    void testGreaterThanExpression() {
        RuleExpression expr = new GreaterThanExpression("total", 100.0);
        assertTrue(expr.interpret(new OrderQueryContext(Map.of("total", 150.0))));
        assertFalse(expr.interpret(new OrderQueryContext(Map.of("total", 100.0))));
        assertFalse(expr.interpret(new OrderQueryContext(Map.of("total", 50.0))));
    }

    @Test
    void testComplexDiscountRule() {
        // (amount > 100 AND customerType == "VIP") OR couponCode == "FREESHIP"
        RuleExpression rule = new OrExpression(
                new AndExpression(
                        new GreaterThanExpression("amount", 100.0),
                        new EqualsExpression("customerType", "VIP")
                ),
                new EqualsExpression("couponCode", "FREESHIP")
        );

        OrderQueryContext vipPassed = new OrderQueryContext(Map.of(
                "amount", 120.0,
                "customerType", "VIP",
                "couponCode", "NONE"
        ));
        assertTrue(rule.interpret(vipPassed));

        OrderQueryContext couponPassed = new OrderQueryContext(Map.of(
                "amount", 20.0,
                "customerType", "GUEST",
                "couponCode", "FREESHIP"
        ));
        assertTrue(rule.interpret(couponPassed));

        OrderQueryContext failed = new OrderQueryContext(Map.of(
                "amount", 50.0,
                "customerType", "GUEST",
                "couponCode", "NONE"
        ));
        assertFalse(rule.interpret(failed));
    }
}
