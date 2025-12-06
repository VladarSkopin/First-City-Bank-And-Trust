package com.firstcitybank.trustbank.database.currency;


import com.firstcitybank.trustbank.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRowMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Currency(
                rs.getString("currency_code"),
                rs.getString("currency_name"),
                rs.getString("currency_symbol"),
                rs.getString("metal_type"));
    }
}
