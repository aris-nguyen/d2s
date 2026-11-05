package io.github.arisnguyen.d2s.pattern.structural.adapter;

import java.util.Objects;

/**
 * Adapter: Translates modern PaymentRequest into legacy XML payload,
 * delegates execution to LegacyXmlPaymentService, and converts the XML response
 * into a modern PaymentResponse object.
 */
public class XmlPaymentGatewayAdapter implements PaymentGateway {
    private final LegacyXmlPaymentService legacyService;

    public XmlPaymentGatewayAdapter(LegacyXmlPaymentService legacyService) {
        this.legacyService = Objects.requireNonNull(legacyService, "legacyService must not be null");
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request must not be null");
        }

        String xmlRequest = String.format(
            "<payment><orderId>%s</orderId><amount>%.2f</amount><currency>%s</currency><email>%s</email></payment>",
            request.getOrderId(),
            request.getAmount(),
            request.getCurrency(),
            request.getCustomerEmail()
        );

        String xmlResponse = legacyService.executeXmlTransaction(xmlRequest);
        return parseResponse(xmlResponse);
    }

    private PaymentResponse parseResponse(String xmlResponse) {
        String status = extractTag(xmlResponse, "status");
        String transId = extractTag(xmlResponse, "transId");
        String msg = extractTag(xmlResponse, "msg");

        boolean success = "SUCCESS".equalsIgnoreCase(status);
        return new PaymentResponse(transId, success, msg);
    }

    private String extractTag(String xml, String tag) {
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";
        int start = xml.indexOf(openTag);
        int end = xml.indexOf(closeTag);
        if (start != -1 && end != -1 && end > start) {
            return xml.substring(start + openTag.length(), end).trim();
        }
        return "";
    }
}
