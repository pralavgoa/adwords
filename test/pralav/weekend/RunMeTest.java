package pralav.weekend;

import java.io.IOException;

import org.junit.Test;

public class RunMeTest {

    @Test
    public void runMeTest() throws IOException {
        // String[] inputParams =
        // {"C:\\_Pralav\\workspace\\Adwords\\data\\unit_test\\v2","1"};
        // String[] inputParams = { "C:\\_Pralav\\personal\\projects\\adwords",
        // "1" };

        // String[] inputParams = {
        // "C:\\_Pralav\\personal\\projects\\adwords\\test", "1" };

        String[] inputParams = { "C:\\_Pralav\\personal\\projects\\adwords\\april_data", "1" };
        RunMe.main(inputParams);
    }
}
