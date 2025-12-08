package com.firstcitybank.trustbank.database.client;

import com.firstcitybank.trustbank.database.dao.ClientDao;
import com.firstcitybank.trustbank.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDataAccessService implements ClientDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> selectClients() {
        var sql = """
                SELECT client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked
                FROM clients
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper());
    }

    @Override
    public int insertClient(Client client) {
        return 0;
    }

    @Override
    public boolean existsByName(String clientName) {
        return false;
    }

    @Override
    public boolean existsByCode(String clientCode) {
        return false;
    }

    @Override
    public int deleteClient(String clientCode) {
        var sql = """
                DELETE FROM clients
                WHERE client_code = ?
                """;
        return jdbcTemplate.update(sql, clientCode);
    }

    @Override
    public Optional<Client> selectClientByCode(String clientCode) {
        var sql = """
                SELECT client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked
                FROM clients
                WHERE client_code = ?
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), clientCode)
                .stream()
                .findFirst();
    }
}
