package io.github.arisnguyen.d2s.pattern.behavioral.templatemethod;

public class CsvDataMiner extends DataMiner {
    @Override
    protected String openFile(String path) {
        return "Opening CSV file at " + path;
    }

    @Override
    protected String extractData() {
        return "Extracting comma-separated lines";
    }

    @Override
    protected String parseData() {
        return "Parsing rows and columns";
    }
}
