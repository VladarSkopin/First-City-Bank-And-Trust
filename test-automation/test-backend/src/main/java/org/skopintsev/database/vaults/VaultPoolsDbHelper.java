package org.skopintsev.database.vaults;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.CommonDatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VaultPoolsDbHelper {

    private static VaultPoolsDb mapRow(ResultSet rs) {
        try {
            return VaultPoolsDb.builder()
                    .id(Long.valueOf(rs.getString("id")))
                    .vaultPoolName(rs.getString("vault_pool_name"))
                    .isArchived(rs.getBoolean("is_archived"))
                    .currencyCode(rs.getString("currency_code"))
                    .sectorCode(rs.getString("sector_code"))
                    .amountFrom(Long.valueOf(rs.getString("amount_from")))
                    .amountTo(Long.valueOf(rs.getString("amount_to")))
                    .createdFrom(rs.getObject("created_from", LocalDateTime.class))
                    .createdTo(rs.getObject("created_to", LocalDateTime.class))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping VaultPoolsDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select vault pool by name: {vaultPoolName}")
    public static VaultPoolsDb selectVaultPoolByName(String vaultPoolName) {
        String query = "SELECT id, vault_pool_name, is_archived, currency_code, sector_code, amount_from, amount_to, created_from, created_to FROM vault_pools WHERE vault_pool_name = ?";
        return CommonDatabaseHelper.queryForObject(query, VaultPoolsDbHelper::mapRow, vaultPoolName);
    }

    @Step("Insert new vault pool: {vaultPoolsDb}")
    public static int insertVaultPool(VaultPoolsDb vaultPoolsDb) {
        String query = """
                INSERT INTO vault_pools (vault_pool_name, is_archived, currency_code, sector_code, amount_from, amount_to, created_from, created_to)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return CommonDatabaseHelper.executeUpdate(
                query,
                vaultPoolsDb.getVaultPoolName(),
                vaultPoolsDb.getIsArchived(),
                vaultPoolsDb.getCurrencyCode(),
                vaultPoolsDb.getSectorCode(),
                vaultPoolsDb.getAmountFrom(),
                vaultPoolsDb.getAmountTo(),
                vaultPoolsDb.getCreatedFrom(),
                vaultPoolsDb.getCreatedTo()
        );
    }

    @Step("Delete all test vault pools.")
    public static void deleteAllTestVaultPools() {
        String query = "DELETE FROM vault_pools WHERE vault_pool_name LIKE 'TEST-%'";
        CommonDatabaseHelper.executeUpdate(query);
    }

    @Step("Get vault pools count.")
    public static int getVaultPoolsCount() {
        String query = "SELECT COUNT(*) FROM vault_pools";
        Integer count = CommonDatabaseHelper.queryForObject(query,
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
