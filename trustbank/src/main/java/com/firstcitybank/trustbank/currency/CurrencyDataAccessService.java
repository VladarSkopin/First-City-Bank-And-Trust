package com.firstcitybank.trustbank.currency;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CurrencyDataAccessService implements CurrencyDao {

    private final JdbcTemplate jdbcTemplate;

    public CurrencyDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Currency> selectCurrencies() {
        var sql = """
                SELECT currency_code, currency_name, currency_symbol
                FROM currencies
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new CurrencyRowMapper());
    }

    @Override
    public int insertCurrency(Currency currency) {
        var sql = """
            INSERT INTO currencies(currency_code, currency_name, currency_symbol)
            VALUES (?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                currency.currencyCode(),
                currency.currencyName(),
                currency.currencySymbol()
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String currencyName) {
        var sql = "SELECT COUNT(*) FROM currencies WHERE currency_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, currencyName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String currencyCode) {
        var sql = "SELECT COUNT(*) FROM currencies WHERE currency_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, currencyCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteCurrency(String currencyCode) {
        var sql = """
                DELETE FROM currencies
                WHERE currency_code = ?
                """;
        return jdbcTemplate.update(sql, currencyCode);
    }

    @Override
    public Optional<Currency> selectCurrencyByCode(String currencyCode) {
        var sql = """
                SELECT currency_code, currency_name, currency_symbol
                FROM currencies
                WHERE currency_code = ?
                """;
        return jdbcTemplate.query(sql, new CurrencyRowMapper(), currencyCode)
                .stream()
                .findFirst();
    }
}
