package com.firstcitybank.trustbank.database.client_type;

import com.firstcitybank.trustbank.database.dao.ClientTypeDao;
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
        return 0;
    }

    @Override
    public boolean existsByName(String clientTypeName) {
        return false;
    }

    @Override
    public boolean existsByCode(String clientTypeCode) {
        return false;
    }

    @Override
    public int deleteClientType(String clientTypeCode) {
        return 0;
    }

    @Override
    public Optional<ClientType> selectClientTypeByCode(String clientTypeCode) {
        return Optional.empty();
    }
}
