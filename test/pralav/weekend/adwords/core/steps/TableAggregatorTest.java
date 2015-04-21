package pralav.weekend.adwords.core.steps;

import java.io.IOException;

import org.junit.Test;

public class TableAggregatorTest {

    @Test
    public void testTableAggregator() throws IOException {
        String inputFileFolder = "E:\\_Pralav\\_Projects\\Adwords\\april_data\\data";
        TableAggregator ta = new TableAggregator(inputFileFolder);
        ta.createAggregateTablePerInputTable();
    }

}
