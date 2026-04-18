package com.firstcitybank.trustbank.database.vault;

import com.firstcitybank.trustbank.model.vault.vault_pools.VaultPool;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VaultPoolRowMapper implements RowMapper<VaultPool> {

    @Override
    public VaultPool mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new VaultPool(
                rs.getLong("id"),
                rs.getString("vault_pool_name"),
                rs.getBoolean("is_archived"),
                rs.getString("currency_code"),
                rs.getString("sector_code"),
                rs.getLong("amount_from"),
                getLongOrNull(rs, "amount_to"),
                rs.getTimestamp("created_from").toLocalDateTime(),
                rs.getTimestamp("created_to").toLocalDateTime()
        );
    }

    private Long getLongOrNull(ResultSet rs, String column) throws SQLException {
        long value = rs.getLong(column);
        return rs.wasNull() ? null : value;
    }
}
