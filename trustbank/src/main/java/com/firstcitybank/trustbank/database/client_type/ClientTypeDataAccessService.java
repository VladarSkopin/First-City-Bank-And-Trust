package com.firstcitybank.trustbank.database.client_type;

import com.firstcitybank.trustbank.database.dao.ClientTypeDao;
import com.firstcitybank.trustbank.helper.Utils;
import com.firstcitybank.trustbank.model.ClientType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientTypeDataAccessService implements ClientTypeDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientTypeDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClientType> selectClientTypes() {
        var sql = """
                SELECT client_type_code, client_type_name, description
                FROM client_types
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new ClientTypeRowMapper());
    }

    @Override
    public int insertClientType(ClientType clientType) {
        var sql = """
            INSERT INTO client_types (client_type_code, client_type_name, description)
            VALUES (?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                clientType.clientTypeCode().toUpperCase().trim(),
                Utils.validateAndGetClientTypeName(clientType.clientTypeName()),
                clientType.description() != null ? clientType.description().trim() : null
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String clientTypeName) {
        var sql = "SELECT COUNT(*) FROM client_types WHERE client_type_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientTypeName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String clientTypeCode) {
        var sql = "SELECT COUNT(*) FROM client_types WHERE client_type_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientTypeCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteClientType(String clientTypeCode) {
        var sql = """
                DELETE FROM client_types
                WHERE client_type_code = ?
                """;
        return jdbcTemplate.update(sql, clientTypeCode);
    }

    @Override
    public Optional<ClientType> selectClientTypeByCode(String clientTypeCode) {
        var sql = """
                SELECT client_type_code, client_type_name, description
                FROM client_types
                WHERE client_type_code = ?
                """;
        return jdbcTemplate.query(sql, new ClientTypeRowMapper(), clientTypeCode)
                .stream()
                .findFirst();
    }
}
