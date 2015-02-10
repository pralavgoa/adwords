package pralav.weekend.database;

import java.sql.SQLException;

import org.junit.Test;

import pralav.weekend.adwords.core.SearchTermMetrics;

public class DBTest {

    @Test
    public void testDB() throws SQLException {
        DB.persist(new SearchTermMetrics("A", "A", 1, 1, 1, 1, 1));
    }
}
