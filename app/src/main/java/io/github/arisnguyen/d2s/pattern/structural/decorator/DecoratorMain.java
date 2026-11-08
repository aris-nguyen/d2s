package io.github.arisnguyen.d2s.pattern.structural.decorator;

public class DecoratorMain {
    public static void main(String[] args) {
        DataSource memory = new MemoryDataSource();
        DataSource pipeline = new CompressionDecorator(new EncryptionDecorator(memory));

        pipeline.writeData("Hello Decorator Pattern");
        System.out.println("Stored raw: " + memory.readData());
        System.out.println("Decoded result: " + pipeline.readData());
    }
}

