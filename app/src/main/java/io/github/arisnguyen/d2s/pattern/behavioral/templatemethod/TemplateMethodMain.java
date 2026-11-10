package io.github.arisnguyen.d2s.pattern.behavioral.templatemethod;

public class TemplateMethodMain {
    public static void main(String[] args) {
        DataMiner pdfMiner = new PdfDataMiner();
        pdfMiner.mine("/docs/invoice.pdf");
        System.out.println("PDF Mining Steps: " + pdfMiner.getExecutionSteps());

        DataMiner csvMiner = new CsvDataMiner();
        csvMiner.mine("/data/report.csv");
        System.out.println("CSV Mining Steps: " + csvMiner.getExecutionSteps());
    }
}
