package io.github.arisnguyen.d2s.pattern.structural.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Decorator Pattern Tests - Data Source Stream")
class DataSourceDecoratorTest {

    @Test
    @DisplayName("Should read and write plain text without modification in MemoryDataSource")
    void testMemoryDataSource() {
        DataSource source = new MemoryDataSource();
        source.writeData("Hello World");

        assertEquals("Hello World", source.readData());
    }

    @Test
    @DisplayName("Should encrypt data upon writing and decrypt upon reading")
    void testEncryptionDecorator() {
        MemoryDataSource memorySource = new MemoryDataSource();
        DataSource encryptedSource = new EncryptionDecorator(memorySource);

        String rawData = "Confidential User Data";
        encryptedSource.writeData(rawData);

        // Underlying storage contains encrypted (Base64) data, not raw text
        assertNotEquals(rawData, memorySource.readData());
        // Reading through the decorator restores the original text
        assertEquals(rawData, encryptedSource.readData());
    }

    @Test
    @DisplayName("Should compress data upon writing and decompress upon reading")
    void testCompressionDecorator() {
        MemoryDataSource memorySource = new MemoryDataSource();
        DataSource compressedSource = new CompressionDecorator(memorySource);

        String longData = "RepeatableText".repeat(50);
        compressedSource.writeData(longData);

        assertNotEquals(longData, memorySource.readData());
        assertEquals(longData, compressedSource.readData());
    }

    @Test
    @DisplayName("Should correctly stack both encryption and compression decorators")
    void testStackedDecorators() {
        MemoryDataSource memorySource = new MemoryDataSource();
        // Stack: CompressionDecorator wrapping EncryptionDecorator wrapping MemoryDataSource
        DataSource stackedSource = new CompressionDecorator(
            new EncryptionDecorator(memorySource)
        );

        String payload = "Complex sensitive message to be encrypted and compressed";
        stackedSource.writeData(payload);

        assertNotEquals(payload, memorySource.readData());
        assertEquals(payload, stackedSource.readData());
    }

    @Test
    @DisplayName("Should throw exception when wrapping null data source")
    void testNullWrappeeThrowsException() {
        assertThrows(NullPointerException.class, () -> new EncryptionDecorator(null));
        assertThrows(NullPointerException.class, () -> new CompressionDecorator(null));
    }
}
