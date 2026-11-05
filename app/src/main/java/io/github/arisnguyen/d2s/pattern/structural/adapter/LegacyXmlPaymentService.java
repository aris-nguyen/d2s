package io.github.arisnguyen.d2s.pattern.structural.adapter;

import java.util.UUID;

/**
 * Adaptee: A legacy 3rd-party payment service accepting and returning XML documents.
 */
public class LegacyXmlPaymentService {

    public String executeXmlTransaction(String xmlPayload) {
        if (xmlPayload == null || !xmlPayload.contains("<payment>")) {
            return "<response><status>ERROR</status><transId>NONE</transId><msg>Invalid XML</msg></response>";
        }

        String orderId = extractTag(xmlPayload, "orderId");
        String amountStr = extractTag(xmlPayload, "amount");

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                return "<response><status>FAILED</status><transId>NONE</transId><msg>Amount must be positive</msg></response>";
            }
            String transId = "LEGACY-" + UUID.randomUUID().toString().substring(0, 8);
            return String.format(
                "<response><status>SUCCESS</status><transId>%s</transId><msg>Processed order %s</msg></response>",
                transId, orderId
            );
        } catch (NumberFormatException e) {
            return "<response><status>FAILED</status><transId>NONE</transId><msg>Invalid amount format</msg></response>";
        }
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
