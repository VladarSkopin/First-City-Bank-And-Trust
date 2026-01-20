package org.skopintsev.database;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
     * Generic query execution with result mapping
     */
    @Step("Execute SELECT query: {query}")
    public static <T> List<T> executeQuery(String query, Function<ResultSet, T> rowMapper, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> results = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(rowMapper.apply(rs));
            }

            log.info("Query executed successfully. Found {} records", results.size());
            Allure.addAttachment("SQL Query Result", "text/plain", String.valueOf(results));
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
     * Generic query for single result
     */
    public static <T> T queryForObject(String query, Function<ResultSet, T> rowMapper, Object... params) {
        List<T> results = executeQuery(query, rowMapper, params);
        return results.isEmpty() ? null : results.get(0);
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