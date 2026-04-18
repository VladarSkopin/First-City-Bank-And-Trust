package com.firstcitybank.trustbank.database.vault;

import com.firstcitybank.trustbank.database.dao.VaultPoolsDao;
import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class VaultPoolsDataAccessService implements VaultPoolsDao {

    private final JdbcTemplate jdbcTemplate;

    public VaultPoolsDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public int insertVaultPool(VaultPool vaultPool) {
        var sql = """
                INSERT INTO vault_pools 
                (vault_pool_name, is_archived, currency_code, sector_code, 
                 amount_from, amount_to, created_from, created_to)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        // Validate foreign keys exist
        validateCurrencyCode(vaultPool.currencyCode());
        validateSectorCode(vaultPool.sectorCode());

        int rowsAffected = jdbcTemplate.update(
                sql,
                vaultPool.vaultPoolName(),
                vaultPool.isArchived(),
                vaultPool.currencyCode().toUpperCase().trim(),
                vaultPool.sectorCode().toUpperCase().trim(),
                vaultPool.amountFrom(),
                vaultPool.amountTo(),
                Timestamp.valueOf(vaultPool.createdFrom()),
                vaultPool.createdTo() != null ? Timestamp.valueOf(vaultPool.createdTo()) : Timestamp.valueOf(vaultPool.createdFrom())
        );

        if (rowsAffected != 1) {
            throw new IllegalStateException("Failed to insert vault pool");
        }
        return rowsAffected;
    }

    @Override
    public Optional<VaultPool> selectVaultPoolById(Long id) {
        var sql = """
                SELECT id, vault_pool_name, is_archived, currency_code, sector_code,
                       amount_from, amount_to, created_from, created_to
                FROM vault_pools
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, new VaultPoolRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<VaultPool> selectVaultPoolByName(String vaultPoolName) {
        var sql = """
                SELECT id, vault_pool_name, is_archived, currency_code, sector_code,
                       amount_from, amount_to, created_from, created_to
                FROM vault_pools
                WHERE vault_pool_name = ?
                """;
        return jdbcTemplate.query(sql, new VaultPoolRowMapper(), vaultPoolName)
                .stream()
                .findFirst();
    }

    @Override
    public boolean existsByName(String vaultPoolName) {
        var sql = "SELECT COUNT(*) FROM vault_pools WHERE vault_pool_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, vaultPoolName);
        return count != null && count > 0;
    }

    @Override
    public int deleteVaultPoolByName(String vaultPoolName) {
        var sql = """
            DELETE FROM vault_pools
            WHERE vault_pool_name = ?
            """;
        return jdbcTemplate.update(sql, vaultPoolName);
    }

    @Override
    public List<VaultPool> selectAllVaultPools() {
        var sql = """
            SELECT id, vault_pool_name, is_archived, currency_code, sector_code,
                   amount_from, amount_to, created_from, created_to
            FROM vault_pools
            ORDER BY id
            """;
        return jdbcTemplate.query(sql, new VaultPoolRowMapper());
    }


    // ---------- Helper validation methods ----------

    private void validateCurrencyCode(String currencyCode) {
        String normalizedCode = currencyCode.trim().toUpperCase();
        var checkSql = "SELECT COUNT(*) FROM currencies WHERE currency_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);
        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid currency code: '" + normalizedCode + "'. Code does not exist."
            );
        }
    }

    private void validateSectorCode(String sectorCode) {
        String normalizedCode = sectorCode.trim().toUpperCase();
        var checkSql = "SELECT COUNT(*) FROM sectors WHERE sector_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);
        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid sector code: '" + normalizedCode + "'. Code does not exist."
            );
        }
    }
}
