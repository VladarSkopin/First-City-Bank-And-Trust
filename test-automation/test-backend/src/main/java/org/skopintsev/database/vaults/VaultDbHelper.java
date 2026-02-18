package org.skopintsev.database.vaults;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VaultDbHelper {

    private static VaultDb mapRow(ResultSet rs) {
        try {
            return VaultDb.builder()
                    .vaultCode(rs.getString("vault_code"))
                    .clientCode(rs.getString("client_code"))
                    .createdAt(rs.getObject("created_at", LocalDateTime.class))
                    .modifiedAt(rs.getObject("modified_at", LocalDateTime.class))
                    .amount(BigInteger.valueOf(rs.getLong("amount")))
                    .currencyCode(rs.getString("currency_code"))
                    .isArchived(rs.getBoolean("is_archived"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping VaultDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select vault by code: {vaultCode}")
    public static VaultDb selectVaultByCode(String vaultCode) {
        String query = "SELECT vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived FROM vault WHERE vault_code = ?";
        return DatabaseHelper.queryForObject(query, VaultDbHelper::mapRow, vaultCode);
    }

    @Step("Insert new vault: {vaultDb}")
    public static int insertVault(VaultDb vaultDb) {
        String query = """
                INSERT INTO vault(vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        return DatabaseHelper.executeUpdate(
                query,
                vaultDb.getVaultCode(),
                vaultDb.getClientCode(),
                vaultDb.getCreatedAt(),
                vaultDb.getModifiedAt(),
                vaultDb.getAmount(),
                vaultDb.getCurrencyCode(),
                vaultDb.getIsArchived()
        );
    }

    @Step("Reset vault amount to zero for vault code: {vaultCode}")
    public static void resetVaultAmountToZero(String vaultCode) {
        String query = "UPDATE vault SET amount = ?, modified_at = ? WHERE vault_code = ?";
        DatabaseHelper.executeUpdate(
                query,
                BigInteger.ZERO,        // amount set to 0
                LocalDateTime.now(),    // update modified timestamp
                vaultCode
        );
    }

    @Step("Delete all test vaults.")
    public static void deleteAllTestVaults() {
        String query = "DELETE FROM vault WHERE vault_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get vaults count.")
    public static int getVaultsCount() {
        String query = "SELECT COUNT(*) FROM vault";
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
