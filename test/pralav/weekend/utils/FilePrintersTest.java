package pralav.weekend.utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import pralav.weekend.database.DB;

public class FilePrintersTest {

    @Test
    public void testFilePrinters() throws SQLException {
        StringBuilder sql = new StringBuilder();

        Map<String, String> mapOfFileToTable = DB
                .selectTwoColumns("select filename, table_name from map_file_to_table");

        for (String filename : mapOfFileToTable.keySet()) {
            String tableName = mapOfFileToTable.get(filename);
            sql.append(this.generateSQLForSingleFile(filename, tableName));
        }

        System.out.println(sql);

    }

    public CharSequence generateSQLForSingleFile(String filename, String tableName) throws SQLException {
        StringBuilder result = new StringBuilder();
        result.append(FilePrinters.generateSQLForDB(filename, tableName));
        List<String> campaigns = DB.selectOneColumn("select distinct campaign from " + tableName);
        result.append(FilePrinters.generateSQLForDBForCampaign(campaigns, filename, tableName));
        return result;
    }
}
