package io.github.arisnguyen.d2s.pattern.structural.adapter;

public class AdapterMain {
    public static void main(String[] args) {
        LegacyXmlPaymentService legacyService = new LegacyXmlPaymentService();
        PaymentGateway gateway = new XmlPaymentGatewayAdapter(legacyService);

        PaymentRequest request = new PaymentRequest("ORD-1001", 150.00, "USD", "alice@example.com");
        PaymentResponse response = gateway.processPayment(request);

        System.out.println("Success: " + response.isSuccess());
        System.out.println("Txn ID: " + response.getTransactionId());
    }
}
