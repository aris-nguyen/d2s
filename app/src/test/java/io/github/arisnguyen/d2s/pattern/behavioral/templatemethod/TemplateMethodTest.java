package io.github.arisnguyen.d2s.pattern.behavioral.templatemethod;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateMethodTest {

    @Test
    void testPdfDataMinerWorkflow() {
        DataMiner miner = new PdfDataMiner();
        miner.mine("sample.pdf");

        List<String> steps = miner.getExecutionSteps();
        assertEquals(4, steps.size());
        assertEquals("Opening PDF file at sample.pdf", steps.get(0));
        assertEquals("Extracting raw text and streams from PDF", steps.get(1));
        assertEquals("Parsing PDF structure and tables", steps.get(2));
        assertEquals("Closing file", steps.get(3));
    }

    @Test
    void testCsvDataMinerWorkflow() {
        DataMiner miner = new CsvDataMiner();
        miner.mine("sample.csv");

        List<String> steps = miner.getExecutionSteps();
        assertEquals(4, steps.size());
        assertEquals("Opening CSV file at sample.csv", steps.get(0));
        assertEquals("Extracting comma-separated lines", steps.get(1));
        assertEquals("Parsing rows and columns", steps.get(2));
        assertEquals("Closing file", steps.get(3));
    }
}
