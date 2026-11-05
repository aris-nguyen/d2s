package io.github.arisnguyen.d2s.pattern.structural.decorator;

/**
 * Component interface declaring read/write operations for data sources.
 */
public interface DataSource {
    void writeData(String data);
    String readData();
}
