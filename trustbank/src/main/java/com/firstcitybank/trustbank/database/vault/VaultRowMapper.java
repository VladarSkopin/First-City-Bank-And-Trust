package com.firstcitybank.trustbank.database.vault;

import com.firstcitybank.trustbank.model.vault.Vault;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VaultRowMapper implements RowMapper<Vault> {
    @Override
    public Vault mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Convert long to BigInteger
        long amountValue = rs.getLong("amount");
        BigInteger amount = BigInteger.valueOf(amountValue);

        return new Vault(
                rs.getString("vault_code"),
                rs.getString("client_code"),
                amount,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime(),
                rs.getString("currency_code"),
                rs.getBoolean("is_archived")
        );
    }
}
