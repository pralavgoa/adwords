package pralav.weekend.adwords.core.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AggregateTableCreatorTest {

    @Test
    public void testAggregateTableCreator() throws IOException {
        AggregateTableCreator agg = new AggregateTableCreator();

        List<String> tableNames = new ArrayList<String>();
        tableNames.add("table1");
        tableNames.add("table2");

        agg.combineAggregateTablesToStaging(tableNames, "staging");
    }
}
