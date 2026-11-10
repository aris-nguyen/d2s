package io.github.arisnguyen.d2s.pattern.behavioral.templatemethod;

public class PdfDataMiner extends DataMiner {
    @Override
    protected String openFile(String path) {
        return "Opening PDF file at " + path;
    }

    @Override
    protected String extractData() {
        return "Extracting raw text and streams from PDF";
    }

    @Override
    protected String parseData() {
        return "Parsing PDF structure and tables";
    }
}
