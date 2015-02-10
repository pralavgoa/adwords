package pralav.weekend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pralav.weekend.adwords.core.SearchTermMetrics;
import pralav.weekend.adwords.core.TokensCollection;

import com.google.common.collect.Table.Cell;

public class DB {

    private static Connection connection;

    public static void persistAll(TokensCollection tableOfTokens) throws SQLException {
        for (Cell<String, String, SearchTermMetrics> cell : tableOfTokens.cellSet()) {
            persist(cell.getValue());
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
