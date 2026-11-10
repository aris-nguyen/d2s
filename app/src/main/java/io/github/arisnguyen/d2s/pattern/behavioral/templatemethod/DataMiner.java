package io.github.arisnguyen.d2s.pattern.behavioral.templatemethod;

import java.util.ArrayList;
import java.util.List;

public abstract class DataMiner {
    private final List<String> executionSteps = new ArrayList<>();

    public final void mine(String path) {
        executionSteps.add(openFile(path));
        executionSteps.add(extractData());
        executionSteps.add(parseData());
        executionSteps.add(closeFile());
    }

    protected abstract String openFile(String path);
    protected abstract String extractData();
    protected abstract String parseData();

    protected String closeFile() {
        return "Closing file";
    }

    public List<String> getExecutionSteps() {
        return executionSteps;
    }
}
