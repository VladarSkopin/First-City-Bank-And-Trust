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
        return List.of();
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
        return 0;
    }

    @Override
    public Optional<Client> selectClientByCode(String clientCode) {
        return Optional.empty();
    }
}
