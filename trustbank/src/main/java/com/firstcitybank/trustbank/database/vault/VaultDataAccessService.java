package com.firstcitybank.trustbank.database.vault;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.model.vault.Vault;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class VaultDataAccessService implements VaultDao {

    private final JdbcTemplate jdbcTemplate;

    public VaultDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Vault> selectVaults() {
        var sql = """
                SELECT vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived
                FROM vault
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper());
    }

    @Override
    public List<Vault> selectVaultsByCurrencyCode(String currencyCode) {
        var sql = """
                SELECT vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived
                FROM vault
                WHERE currency_code = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), currencyCode.trim().toUpperCase());
    }

    @Override
    public List<Vault> selectVaultsByClientCode(String clientCode) {
        var sql = """
                SELECT vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived
                FROM vault
                WHERE client_code = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), clientCode.trim().toUpperCase());
    }

    @Override
    public List<Vault> selectVaultsByClientName(String clientName) {
        var sql = """
                SELECT v.*
                FROM vault v
                JOIN clients c ON v.client_code = c.client_code
                WHERE c.name_or_title = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), clientName.trim());
    }

    @Override
    public List<Vault> selectVaultsByClientRank(String rankCode) {
        var sql = """
                SELECT v.*
                FROM vault v
                JOIN clients c ON v.client_code = c.client_code
                WHERE c.social_rank_code = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), rankCode.trim().toUpperCase());
    }

    @Override
    public List<Vault> selectVaultsByClientType(String typeCode) {
        var sql = """
                SELECT v.*
                FROM vault v
                JOIN clients c ON v.client_code = c.client_code
                WHERE c.client_type_code = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), typeCode.trim().toUpperCase());
    }

    @Override
    public List<Vault> selectVaultsByClientSector(String sectorCode) {
        var sql = """
                SELECT v.*
                FROM vault v
                JOIN clients c ON v.client_code = c.client_code
                JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code
                JOIN sectors s ON ss.sector_code = s.sector_code
                WHERE s.sector_code = ?
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), sectorCode.trim().toUpperCase());
    }


    @Override
    public int insertVault(Vault vault) {
        var sql = """
                INSERT INTO vault (vault_code, client_code, amount, currency_code, is_archived)
                VALUES (?, ?, ?, ?, ?)
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                vault.vaultCode().toUpperCase().trim(),
                validateAndGetClientCode(vault.clientCode()),
                vault.amount(),
                validateAndGetCurrencyCode(vault.currencyCode()),
                vault.isArchived()
        );

        return rowsAffected;
    }

    @Override
    @Transactional
    public int insertAmount(String vaultCode, BigInteger amountToInsert) {
        validateInsertAmountParameters(vaultCode, amountToInsert);

        // Use PostgreSQL's RETURNING clause to get updated row
        var sql = """
                UPDATE vault 
                SET amount = amount + ?,
                    modified_at = CURRENT_TIMESTAMP
                WHERE vault_code = ?
                  AND is_archived = false
                  AND EXISTS (
                      SELECT 1 FROM clients 
                      WHERE client_code = vault.client_code 
                      AND is_blocked = false
                  )
                RETURNING amount
                """;

        try {
            // Execute update and get the new amount
            BigInteger newAmount = jdbcTemplate.queryForObject(
                    sql,
                    BigInteger.class,
                    amountToInsert,
                    vaultCode.trim().toUpperCase()
            );

            logTransaction(vaultCode, amountToInsert, "INSERT", newAmount);

            return 1; // Success
        } catch (Exception e) {
            throw new IllegalStateException(
                    String.format("Failed to insert amount %s into vault %s: %s",
                            amountToInsert, vaultCode, e.getMessage()),
                    e
            );
        }
    }

    @Override
    @Transactional
    public int withdrawAmount(String vaultCode, BigInteger amountToWithdraw) {
        validateWithdrawAmountParameters(vaultCode, amountToWithdraw);

        // Check if vault has sufficient funds first
        var checkSql = """
                SELECT amount FROM vault 
                WHERE vault_code = ? 
                FOR UPDATE
                """;

        BigInteger currentAmount = jdbcTemplate.queryForObject(
                checkSql,
                BigInteger.class,
                vaultCode.trim().toUpperCase()
        );

        if (currentAmount == null || currentAmount.compareTo(amountToWithdraw) < 0) {
            throw new IllegalStateException(
                    String.format("Insufficient funds in vault '%s'. Available: %s, Requested: %s",
                            vaultCode, currentAmount, amountToWithdraw)
            );
        }

        // Perform the withdrawal
        var updateSql = """
                UPDATE vault 
                SET amount = amount - ?,
                    modified_at = CURRENT_TIMESTAMP
                WHERE vault_code = ?
                  AND is_archived = false
                  AND amount >= ?
                  AND EXISTS (
                      SELECT 1 FROM clients 
                      WHERE client_code = vault.client_code 
                      AND is_blocked = false
                  )
                RETURNING amount
                """;

        try {
            BigInteger newAmount = jdbcTemplate.queryForObject(
                    updateSql,
                    BigInteger.class,
                    amountToWithdraw,
                    vaultCode,
                    amountToWithdraw
            );

            logTransaction(vaultCode, amountToWithdraw, "WITHDRAW", newAmount);

            return 1; // Success
        } catch (Exception e) {
            throw new IllegalStateException(
                    String.format("Failed to withdraw amount %s from vault %s: %s",
                            amountToWithdraw, vaultCode, e.getMessage()),
                    e
            );
        }
    }

    @Override
    public boolean existsByCode(String vaultCode) {
        var sql = "SELECT COUNT(*) FROM vault WHERE vault_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, vaultCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteVault(String vaultCode) {
        var sql = """
                DELETE FROM vault
                WHERE vault_code = ?
                AND amount = 0
                AND is_archived = true
                """;
        return jdbcTemplate.update(sql, vaultCode);
    }

    @Override
    public Optional<Vault> selectVaultByCode(String vaultCode) {
        var sql = """
                SELECT vault_code, client_code, created_at, modified_at, amount, currency_code, is_archived
                FROM vault
                WHERE vault_code = ?
                """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), vaultCode)
                .stream()
                .findFirst();
    }

    @Override
    public List<Vault> selectVaultsBySectorWithLimit(String sectorCode, int limit) {
        var sql = """
            SELECT v.vault_code, v.client_code, v.created_at, v.modified_at, 
                   v.amount, v.currency_code, v.is_archived
            FROM vault v
            JOIN clients c ON v.client_code = c.client_code
            JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code
            JOIN sectors s ON ss.sector_code = s.sector_code
            WHERE s.sector_code = ?
              AND v.is_archived = false
              AND c.is_blocked = false
            LIMIT ?
            """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), sectorCode.trim().toUpperCase(), limit);
    }

    @Override
    public List<Vault> selectVaultsBySubSectorWithLimit(String subSectorCode, int limit) {
        var sql = """
            SELECT v.vault_code, v.client_code, v.created_at, v.modified_at, 
                   v.amount, v.currency_code, v.is_archived
            FROM vault v
            JOIN clients c ON v.client_code = c.client_code
            WHERE c.sub_sector_code = ?
              AND v.is_archived = false
              AND c.is_blocked = false
            LIMIT ?
            """;
        return jdbcTemplate.query(sql, new VaultRowMapper(), subSectorCode.trim().toUpperCase(), limit);
    }


    // Helpers

    private String validateAndGetClientCode(String clientCode) {
        String normalizedCode = clientCode.trim().toUpperCase();

        // Check if client exists in the database
        var checkSql = "SELECT COUNT(*) FROM clients WHERE client_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid client code: '" + normalizedCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }

    private String validateAndGetCurrencyCode(String currencyCode) {
        String normalizedCode = currencyCode.trim().toUpperCase();

        // Check if currency exists in the database
        var checkSql = "SELECT COUNT(*) FROM currencies WHERE currency_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid currency code: '" + normalizedCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }

    private void validateInsertAmountParameters(String vaultCode, BigInteger amountToInsert) {
        if (vaultCode == null || vaultCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Vault code cannot be null or empty");
        }

        if (amountToInsert == null) {
            throw new IllegalArgumentException("Amount to insert cannot be null");
        }

        if (amountToInsert.signum() <= 0) {
            throw new IllegalArgumentException("Amount to insert must be greater than 0");
        }
    }

    private void validateWithdrawAmountParameters(String vaultCode, BigInteger amountToWithdraw) {
        if (vaultCode == null || vaultCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Vault code cannot be null or empty");
        }

        if (amountToWithdraw == null) {
            throw new IllegalArgumentException("Amount to withdraw cannot be null");
        }

        if (amountToWithdraw.signum() <= 0) {
            throw new IllegalArgumentException("Amount to withdraw must be greater than 0");
        }
    }

    private void logTransaction(String vaultCode, BigInteger amount, String operation, BigInteger newBalance) {
        // Optional: Insert into transaction log table
        var logSql = """
                INSERT INTO vault_transactions 
                (vault_code, operation_type, amount, new_balance, transaction_time)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)
                """;

        jdbcTemplate.update(logSql, vaultCode, operation, amount, newBalance);
    }

    // Helper method to check if client is blocked
    private boolean isClientBlocked(String clientCode) {
        var sql = "SELECT is_blocked FROM clients WHERE client_code = ?";
        try {
            Boolean isBlocked = jdbcTemplate.queryForObject(sql, Boolean.class, clientCode);
            return isBlocked != null && isBlocked;
        } catch (Exception e) {
            return false;
        }
    }
}
