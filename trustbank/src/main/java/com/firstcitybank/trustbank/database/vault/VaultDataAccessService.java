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
        return 0;
    }

    @Override
    public int insertAmount(Integer amountToInsert) {
        return 0;
    }

    @Override
    public int withdrawAmount(Integer amountToWithdraw) {
        return 0;
    }

    @Override
    public boolean existsByName(String vaultName) {
        return false;
    }

    @Override
    public boolean existsByCode(String vaultCode) {
        return false;
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
}
