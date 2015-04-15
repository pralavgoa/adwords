package pralav.weekend.database;

import java.sql.SQLException;

import org.junit.Test;

public class DBTest {

    @Test
    public void testDB() throws SQLException {
        // DB.persist(new SearchTermMetrics("A", "A", "A", "A", 1, 1, 1, 1, 1));

        DB.createDataLoadTable("hello");
    }

}
