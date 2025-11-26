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
    public void insertCurrency(Currency currency) {

    }

    @Override
    public boolean existsByName(String currencyName) {
        return false;
    }

    @Override
    public boolean existsByCode(String currencyCode) {
        return false;
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
