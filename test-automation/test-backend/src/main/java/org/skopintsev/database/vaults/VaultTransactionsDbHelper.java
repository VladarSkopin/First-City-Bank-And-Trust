package org.skopintsev.database.vaults;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class VaultTransactionsDbHelper {

    private static VaultTransactionsDb mapRow(ResultSet rs) {
        try {
            return VaultTransactionsDb.builder()
                    .transactionId(Integer.valueOf(rs.getString("transaction_id")))
                    .vaultCode(rs.getString("vault_code"))
                    .operationType(rs.getString("operation_type"))
                    .amount(BigInteger.valueOf(rs.getLong("amount")))
                    .newBalance(BigInteger.valueOf(rs.getLong("new_balance")))
                    .transactionTime(rs.getObject("transaction_time", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping VaultTransactionsDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select vault transactions by vault code: {vaultCode}")
    public static List<VaultTransactionsDb> selectVaultTransactionsByVaultCode(String vaultCode) {
        String query = """
                SELECT transaction_id, vault_code, operation_type, amount, new_balance, transaction_time
                FROM vault_transactions
                WHERE vault_code = ?
                """;
        return DatabaseHelper.executeQuery(query, VaultTransactionsDbHelper::mapRow, vaultCode);
    }

    @SneakyThrows
    @Step("Select the last vault transaction by transaction time.")
    public static VaultTransactionsDb selectLastVaultTransactionByTime() {
        String query = """
                SELECT transaction_id, vault_code, operation_type, amount, new_balance, transaction_time
                FROM vault_transactions
                ORDER BY transaction_time DESC
                LIMIT 1;
                """;
        return DatabaseHelper.queryForObject(query, VaultTransactionsDbHelper::mapRow);
    }

    @Step("Delete all test vault transactions.")
    public static void deleteAllTestVaultTransactions() {
        String query = "DELETE FROM vault_transactions WHERE vault_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get vault transactions count.")
    public static int getVaultTransactionsCount() {
        String query = "SELECT COUNT(*) FROM vault_transactions";
        Integer count = DatabaseHelper.queryForObject(query,
                rs -> {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error getting count", e);
                    }
                }
        );
        return count != null ? count : 0;
    }
}
