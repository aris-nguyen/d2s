package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.Map;

public class InterpreterMain {
    public static void main(String[] args) {
        // Business Rule: (amount > 100 AND customerType == "VIP") OR isFirstOrder == true
        RuleExpression vipDiscountRule = new OrExpression(
                new AndExpression(
                        new GreaterThanExpression("amount", 100.0),
                        new EqualsExpression("customerType", "VIP")
                ),
                new EqualsExpression("isFirstOrder", true)
        );

        OrderQueryContext regularSmallOrder = new OrderQueryContext(Map.of(
                "amount", 50.0,
                "customerType", "REGULAR",
                "isFirstOrder", false
        ));

        OrderQueryContext vipBigOrder = new OrderQueryContext(Map.of(
                "amount", 250.0,
                "customerType", "VIP",
                "isFirstOrder", false
        ));

        OrderQueryContext firstOrder = new OrderQueryContext(Map.of(
                "amount", 30.0,
                "customerType", "REGULAR",
                "isFirstOrder", true
        ));

        System.out.println("Regular small order eligible for discount: " + vipDiscountRule.interpret(regularSmallOrder));
        System.out.println("VIP big order eligible for discount: " + vipDiscountRule.interpret(vipBigOrder));
        System.out.println("First order eligible for discount: " + vipDiscountRule.interpret(firstOrder));
    }
}
