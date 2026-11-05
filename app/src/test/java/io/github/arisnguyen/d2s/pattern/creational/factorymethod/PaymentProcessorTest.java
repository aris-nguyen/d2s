package io.github.arisnguyen.d2s.pattern.creational.factorymethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PaymentProcessorTest {

    @Test
    @DisplayName("CreditCardProcessor creates CreditCardPayment and processes order")
    public void testCreditCardProcessor() {
        PaymentProcessor processor = new CreditCardProcessor("1234567890123456");
        PaymentMethod payment = processor.createPaymentMethod();

        assertNotNull(payment);
        assertInstanceOf(CreditCardPayment.class, payment);

        String result = processor.processOrder(150.0);
        assertTrue(result.contains("Paid $150.0 using Credit Card ending with 3456"));
    }

    @Test
    @DisplayName("PayPalProcessor creates PayPalPayment and processes order")
    public void testPayPalProcessor() {
        PaymentProcessor processor = new PayPalProcessor("customer@example.com");
        PaymentMethod payment = processor.createPaymentMethod();

        assertNotNull(payment);
        assertInstanceOf(PayPalPayment.class, payment);

        String result = processor.processOrder(49.99);
        assertTrue(result.contains("Paid $49.99 via PayPal account [customer@example.com]"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when order amount is not positive")
    public void testInvalidAmountThrowsException() {
        PaymentProcessor processor = new PayPalProcessor("customer@example.com");
        assertThrows(IllegalArgumentException.class, () -> processor.processOrder(0));
        assertThrows(IllegalArgumentException.class, () -> processor.processOrder(-10));
    }
}
