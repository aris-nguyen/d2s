package io.github.arisnguyen.d2s.pattern.structural.decorator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Concrete Decorator that encodes data to Base64 on write and decodes on read.
 */
public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return decode(super.readData());
    }

    private String encode(String data) {
        if (data == null) {
            return "";
        }
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String data) {
        if (data == null || data.isBlank()) {
            return "";
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(data);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return data;
        }
    }
}
