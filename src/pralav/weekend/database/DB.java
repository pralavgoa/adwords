package pralav.weekend.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pralav.weekend.adwords.core.SearchTermMetrics;
import pralav.weekend.adwords.core.TokensCollection;

import com.google.common.collect.Table.Cell;

import freemarker.template.TemplateException;

public class DB {

    private static Connection connection;

    public static void persistAll(TokensCollection tableOfTokens) throws SQLException {
        int tokenNumber = 0;
        for (Cell<String, String, SearchTermMetrics> cell : tableOfTokens.cellSet()) {
            persist(cell.getValue());
            tokenNumber++;
            if ((tokenNumber % 100000) == 0) {
                System.out.println("Processing token:" + tokenNumber);
            }
        }
    }

    public static void batchPersist(List<SearchTermMetrics> list, String tableName) throws SQLException {
        Connection conn = getDBConnection();
        conn.setAutoCommit(false);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO `adwords`.`stm_" + tableName + "` "
                + "(`word`, `campaign`, `impressions`, `clicks`, `converted_clicks`, "
                + "`total_conv_value`, `cost`,`account`,`ad_group`) " + "VALUES (?, ?, ?, ?, ?, ?, ?,?,?)");
        for (int i = 0; i < list.size(); i++) {
            SearchTermMetrics searchTermMetrics = list.get(i);
            stmt.setString(1, searchTermMetrics.getToken());
            stmt.setString(2, searchTermMetrics.getCampaign());
            stmt.setInt(3, searchTermMetrics.getImpressions());
            stmt.setInt(4, searchTermMetrics.getClicks());
            stmt.setInt(5, searchTermMetrics.getConvertedClicks());
            stmt.setDouble(6, searchTermMetrics.getTotalConvValue());
            stmt.setDouble(7, searchTermMetrics.getCost());
            stmt.setString(8, searchTermMetrics.getAccountName());
            stmt.setString(9, searchTermMetrics.getAdGroup());
            stmt.addBatch();
            if (((i + 1) % 10000) == 0) {
                stmt.executeBatch(); // Execute every 10000 items.
                conn.commit();
            }
        }
        stmt.executeBatch();
        conn.commit();
    }

    public static void persist(SearchTermMetrics searchTermMetrics) throws SQLException {
        List<SearchTermMetrics> termList = new ArrayList<>();
        termList.add(searchTermMetrics);
        batchPersist(termList, "tableName");
    }

    public static void executeSQL(String sql) throws SQLException {
        System.out.println("Executing: ");
        System.out.println(sql);
        Connection conn = getDBConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.execute();
    }

    public static List<String> selectOneColumn(String selectStatement) throws SQLException {
        List<String> results = new ArrayList<String>();
        System.out.println("Executing: ");
        System.out.println(selectStatement);
        Connection conn = getDBConnection();
        PreparedStatement stmt = conn.prepareStatement(selectStatement);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            results.add(rs.getString(1));
        }
        return results;
    }

    public static Map<String, String> selectTwoColumns(String selectStatement) throws SQLException {
        Map<String, String> results = new HashMap<String, String>();
        System.out.println("Executing: ");
        System.out.println(selectStatement);
        Connection conn = getDBConnection();
        PreparedStatement stmt = conn.prepareStatement(selectStatement);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            results.put(rs.getString(1), rs.getString(2));
        }
        return results;
    }

    public static Connection getDBConnection() throws SQLException {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            } catch (SQLException e) {
                System.out.println("DataBank init Error: " + e);
            }
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/adwords?useServerPrepStmts=false&rewriteBatchedStatements=true&user=root&password=&autoReconnect=true");
        }
        return connection;
    }

    public static void createDataLoadTable(String name, int wordCount) {
        try {
            SQLTemplateUtil sqlTemplate = new SQLTemplateUtil("sql");
            Map<String, Object> mapOfParameters = new HashMap<>();
            mapOfParameters.put("dataTableName", name + "_" + wordCount);
            String sql = sqlTemplate.getSQLFromTemplate(mapOfParameters, "create_data_table.sql");
            Connection conn = getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
