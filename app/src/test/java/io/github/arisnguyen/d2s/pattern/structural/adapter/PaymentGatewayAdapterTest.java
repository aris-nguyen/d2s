package io.github.arisnguyen.d2s.pattern.structural.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Adapter Pattern Tests - Payment Gateway")
class PaymentGatewayAdapterTest {

    private PaymentGateway paymentGateway;
    private LegacyXmlPaymentService legacyService;

    @BeforeEach
    void setUp() {
        legacyService = new LegacyXmlPaymentService();
        paymentGateway = new XmlPaymentGatewayAdapter(legacyService);
    }

    @Test
    @DisplayName("Should successfully process valid payment through adapter")
    void testProcessPaymentSuccess() {
        PaymentRequest request = new PaymentRequest("ORD-1001", 150.00, "USD", "alice@example.com");
        PaymentResponse response = paymentGateway.processPayment(request);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertTrue(response.getTransactionId().startsWith("LEGACY-"));
        assertTrue(response.getMessage().contains("ORD-1001"));
    }

    @Test
    @DisplayName("Should throw exception when creating request with invalid data")
    void testInvalidRequestData() {
        assertThrows(IllegalArgumentException.class, () ->
            new PaymentRequest(null, 100.0, "USD", "test@test.com")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new PaymentRequest("ORD-1", -10.0, "USD", "test@test.com")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new PaymentRequest("ORD-1", 10.0, "", "test@test.com")
        );
    }

    @Test
    @DisplayName("Should throw exception when null request is passed to adapter")
    void testNullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            paymentGateway.processPayment(null)
        );
    }

    @Test
    @DisplayName("Should throw exception when null legacy service is passed to adapter constructor")
    void testNullServiceThrowsException() {
        assertThrows(NullPointerException.class, () ->
            new XmlPaymentGatewayAdapter(null)
        );
    }
}
