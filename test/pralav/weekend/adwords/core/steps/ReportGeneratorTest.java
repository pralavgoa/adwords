package pralav.weekend.adwords.core.steps;

import java.io.IOException;

import org.junit.Test;

public class ReportGeneratorTest {

    private final int[] wordsTogether = { 2 };

    @Test
    public void testReportGenerator() throws IOException {
        for (int wc : this.wordsTogether) {
            for (int i = 0; i < NameGenerator.campaignShortName.length; i++) {
                String campaignAlias = NameGenerator.campaignShortName[i];
                String inputAggTable = "stm_agg_" + wc + "_" + campaignAlias;
                String outputFolder = "E:\\\\_Pralav\\\\_Projects\\\\Adwords\\\\april_output\\\\" + wc + "\\\\";
                String accountName = NameGenerator.campaignNames[i];
                ReportGenerator rg = new ReportGenerator(accountName, inputAggTable, outputFolder);
                rg.generateReport();
            }
        }
    }
}
