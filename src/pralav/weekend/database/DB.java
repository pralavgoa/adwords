package pralav.weekend.database;

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

    public static void persist(SearchTermMetrics searchTermMetrics) throws SQLException {
        Connection conn = getDBConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO `adwords`.`search_term_metrics` "
                + "(`word`, `campaign`, `impressions`, `clicks`, `converted_clicks`, `total_conv_value`, `cost`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, searchTermMetrics.getToken());
        stmt.setString(2, searchTermMetrics.getCampaign());
        stmt.setInt(3, searchTermMetrics.getImpressions());
        stmt.setInt(4, searchTermMetrics.getClicks());
        stmt.setInt(5, searchTermMetrics.getConvertedClicks());
        stmt.setDouble(6, searchTermMetrics.getTotalConvValue());
        stmt.setDouble(7, searchTermMetrics.getCost());
        stmt.execute();
    }

    public static void saveToFileSelectStatement(String selectStatement) throws SQLException {
        System.out.println("Executing: ");
        System.out.println(selectStatement);
        Connection conn = getDBConnection();
        PreparedStatement stmt = conn.prepareStatement(selectStatement);
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
                    .getConnection("jdbc:mysql://localhost/adwords?user=root&password=&autoReconnect=true");
        }
        return connection;
    }
}
