package com.firstcitybank.trustbank.database.client;

import com.firstcitybank.trustbank.model.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(
                rs.getString("client_code"),
                rs.getString("name_or_title"),
                rs.getString("client_type_code"),
                rs.getString("social_rank_code"),
                rs.getString("district_code"),
                rs.getBoolean("is_blocked"));
    }
}
