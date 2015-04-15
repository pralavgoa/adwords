package pralav.weekend.adwords.core.steps;

import java.io.IOException;

import org.junit.Test;

public class ReportGeneratorTest {

    @Test
    public void testReportGenerator() throws IOException {
        String inputAggTable = "stm_agg";
        String outputFolder = "E:\\\\_Pralav\\\\_Projects\\\\Adwords\\\\april_output\\\\";
        ReportGenerator rg = new ReportGenerator(inputAggTable, outputFolder);
        rg.generateReport();
    }

}
