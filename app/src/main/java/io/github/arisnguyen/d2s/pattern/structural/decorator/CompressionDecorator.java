package io.github.arisnguyen.d2s.pattern.structural.decorator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * Concrete Decorator that compresses data using Deflater on write
 * and decompresses using Inflater on read.
 */
public class CompressionDecorator extends DataSourceDecorator {

    public CompressionDecorator(DataSource wrappee) {
        super(wrappee);
    }

    @Override
    public void writeData(String data) {
        super.writeData(compress(data));
    }

    @Override
    public String readData() {
        return decompress(super.readData());
    }

    private String compress(String stringData) {
        if (stringData == null || stringData.isEmpty()) {
            return "";
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
            deflaterOutputStream.write(stringData.getBytes(StandardCharsets.UTF_8));
            deflaterOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Compression error", e);
        }
    }

    private String decompress(String stringData) {
        if (stringData == null || stringData.isBlank()) {
            return "";
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(stringData);
            InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(bytes));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int read;
            while ((read = inflaterInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inflaterInputStream.close();
            return outputStream.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Decompression error", e);
        }
    }
}
