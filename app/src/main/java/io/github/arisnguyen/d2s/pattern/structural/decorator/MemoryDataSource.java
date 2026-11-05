package io.github.arisnguyen.d2s.pattern.structural.decorator;

import java.util.Objects;

/**
 * Concrete component providing in-memory string storage.
 */
public class MemoryDataSource implements DataSource {
    private String data = "";

    @Override
    public void writeData(String data) {
        this.data = Objects.requireNonNullElse(data, "");
    }

    @Override
    public String readData() {
        return data;
    }
}
