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
        var sql = """
            INSERT INTO clients (client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                client.clientCode().toUpperCase().trim(),
                client.nameOrTitle().trim(),
                validateAndGetClientTypeCode(client.clientTypeCode()),
                validateAndGetSocialRankCode(client.socialRankCode()),
                validateAndGetDistrictCode(client.districtCode()),
                client.isBlocked()
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String clientName) {
        var sql = "SELECT COUNT(*) FROM clients WHERE name_or_title = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String clientCode) {
        var sql = "SELECT COUNT(*) FROM clients WHERE client_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientCode);
        return count != null && count > 0;
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


    // Helpers

    private String validateAndGetClientTypeCode(String clientTypeCode) {
        String normalizedCode = clientTypeCode.trim().toUpperCase();

        // Check if client type exists in the database
        var checkSql = "SELECT COUNT(*) FROM client_types WHERE client_type_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid client type code: '" + clientTypeCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }

    private String validateAndGetSocialRankCode(String socialRankCode) {
        String normalizedCode = socialRankCode.trim().toUpperCase();

        // Check if social rank exists in the database
        var checkSql = "SELECT COUNT(*) FROM social_ranks WHERE rank_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid social rank code: '" + socialRankCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }

    private String validateAndGetDistrictCode(String districtCode) {
        if (districtCode == null) {
            return null; // District can be null
        }

        String normalizedCode = districtCode.trim().toUpperCase();

        // Check if district exists in the database
        var checkSql = "SELECT COUNT(*) FROM districts WHERE district_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid district code: '" + districtCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }
}
