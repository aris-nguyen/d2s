package io.github.arisnguyen.d2s.pattern.structural.facade;

import java.util.UUID;

/**
 * Subsystem 3: Manages courier shipping and dispatch.
 */
public class ShippingService {

    public String shipProduct(String productId, int quantity, String destinationAddress) {
        if (productId == null || destinationAddress == null || destinationAddress.isBlank() || quantity <= 0) {
            throw new IllegalArgumentException("Invalid shipping parameters");
        }
        return "TRACK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
