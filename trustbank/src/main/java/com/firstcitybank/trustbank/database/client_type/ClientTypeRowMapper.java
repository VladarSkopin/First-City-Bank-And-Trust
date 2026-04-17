package com.firstcitybank.trustbank.database.client_type;

import com.firstcitybank.trustbank.model.client.ClientType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientTypeRowMapper implements RowMapper<ClientType> {
    @Override
    public ClientType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ClientType(
                rs.getString("client_type_code"),
                rs.getString("client_type_name"),
                rs.getString("description"));
    }
}
