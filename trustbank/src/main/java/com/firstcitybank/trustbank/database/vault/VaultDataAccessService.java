package com.firstcitybank.trustbank.database.vault;

import com.firstcitybank.trustbank.database.dao.VaultDao;
import com.firstcitybank.trustbank.model.Vault;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public List<Vault> selectVaultsByClientName(String clientName) {
        return List.of();
    }

    @Override
    public List<Vault> selectVaultsByClientCode(String clientCode) {
        return List.of();
    }

    @Override
    public List<Vault> selectNobilityVaults() {
        return List.of();
    }

    @Override
    public List<Vault> selectForeignVaults() {
        return List.of();
    }

    @Override
    public List<Vault> selectGoldenVaults() {
        return List.of();
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
    public int insertAmount(Integer amountToInsert) {
        // todo: check isArchived for vault, isBlocked for client
        // todo: check amountToInsert is > 0
        // todo: update 'modified_at' field
        return 0;
    }

    @Override
    public int withdrawAmount(Integer amountToWithdraw) {
        // todo: check isArchived for vault, isBlocked for client
        // todo: check amountToWithdraw is > 0
        // todo: check amountToWithdraw is <= vault.amount
        // todo: update 'modified_at' field
        return 0;
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
}
