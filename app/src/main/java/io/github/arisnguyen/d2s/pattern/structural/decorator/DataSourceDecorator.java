package io.github.arisnguyen.d2s.pattern.structural.decorator;

import java.util.Objects;

/**
 * Base decorator referencing the wrapped DataSource and delegating operations.
 */
public abstract class DataSourceDecorator implements DataSource {
    protected final DataSource wrappee;

    protected DataSourceDecorator(DataSource wrappee) {
        this.wrappee = Objects.requireNonNull(wrappee, "wrappee must not be null");
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}
