package pralav.weekend.adwords.core.steps;

import java.io.IOException;

import org.junit.Test;

public class TableCollectorTest {
    @Test
    public void testTableCollector() throws IOException {
        String inputFileFolder = "E:\\_Pralav\\_Projects\\Adwords\\april_data\\data";
        TableCollector tc = new TableCollector(inputFileFolder);
        tc.collectData();
    }
}
