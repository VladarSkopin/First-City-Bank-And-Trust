package org.skopintsev.database;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.skopintsev.database.currency.CurrencyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatabaseHelper {
    private static final DatabaseConfig config = DatabaseConfig.getInstance();

    // No static initializer - lazy initialization
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(config.getDriverClassName());
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
        );
    }

    /**
     * Close resources quietly
     */
    private static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) rs.close();
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            log.warn("Error closing database resources", e);
        }
    }

    /**
     * Execute a SELECT query and map results to CurrencyDb objects
     */
    @Step("Execute SELECT query: {query}")
    public static List<CurrencyDb> executeQuery(String query, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<CurrencyDb> results = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(query);

            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            rs = stmt.executeQuery();

            // Attach SQL to Allure report
            Allure.addAttachment("SQL Query", "text/plain", query);

            while (rs.next()) {
                CurrencyDb currency = CurrencyDb.builder()
                        .currencyCode(rs.getString("currency_code"))
                        .currencyName(rs.getString("currency_name"))
                        .currencySymbol(rs.getString("currency_symbol"))
                        .metalType(rs.getString("metal_type"))
                        .build();
                results.add(currency);
            }

            log.info("Query executed successfully. Found {} records", results.size());
            return results;

        } catch (SQLException e) {
            String errorMsg = String.format("Error executing query: %s", query);
            log.error(errorMsg, e);
            Allure.addAttachment("SQL Error", "text/plain", errorMsg + "\n" + e.getMessage());
            throw new RuntimeException(errorMsg, e);
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Execute INSERT/UPDATE/DELETE queries
     */
    @Step("Execute UPDATE query: {query}")
    public static int executeUpdate(String query, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(query);

            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            int rowsAffected = stmt.executeUpdate();

            // Attach SQL to Allure report
            Allure.addAttachment("SQL Update Query", "text/plain", query);
            Allure.addAttachment("Rows Affected", "text/plain", String.valueOf(rowsAffected));

            log.info("Update executed successfully. Rows affected: {}", rowsAffected);
            return rowsAffected;

        } catch (SQLException e) {
            String errorMsg = String.format("Error executing update: %s", query);
            log.error(errorMsg, e);
            Allure.addAttachment("SQL Error", "text/plain", errorMsg + "\n" + e.getMessage());
            throw new RuntimeException(errorMsg, e);
        } finally {
            closeResources(conn, stmt, null);
        }
    }
}