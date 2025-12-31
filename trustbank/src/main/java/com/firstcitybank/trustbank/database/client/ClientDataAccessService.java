package com.firstcitybank.trustbank.database.client;

import com.firstcitybank.trustbank.database.dao.ClientDao;
import com.firstcitybank.trustbank.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
                SELECT client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code
                FROM clients
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper());
    }

    @Override
    public int insertClient(Client client) {
        var sql = """
            INSERT INTO clients (client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                client.clientCode().toUpperCase().trim(),
                client.nameOrTitle().trim(),
                validateAndGetClientTypeCode(client.clientTypeCode()),
                client.socialRankCode() == null ? null : validateAndGetSocialRankCode(client.socialRankCode()),
                validateAndGetDistrictCode(client.districtCode()),
                client.isBlocked(),
                client.subSectorCode() == null ? null : validateAndGetSubSectorCode(client.subSectorCode())
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String clientNameOrTitle) {
        var sql = "SELECT COUNT(*) FROM clients WHERE name_or_title = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, clientNameOrTitle);
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
                SELECT client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked, sub_sector_code
                FROM clients
                WHERE client_code = ?
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), clientCode.trim().toUpperCase())
                .stream()
                .findFirst();
    }

    @Override
    public List<Client> findClientsBySocialRank(String socialRankCode) {
        var sql = """
            SELECT *
            FROM clients
            WHERE social_rank_code = ?
            ORDER BY name_or_title
            """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), socialRankCode.trim().toUpperCase());
    }

    @Override
    public List<Client> findClientsByClientType(String clientTypeCode) {
        var sql = """
            SELECT *
            FROM clients
            WHERE client_type_code = ?
            ORDER BY name_or_title
            """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), clientTypeCode.trim().toUpperCase());
    }

    @Override
    public List<Client> findClientsBySubSector(String subSectorCode) {
        var sql = """
            SELECT *
            FROM clients
            WHERE sub_sector_code = ?
            ORDER BY name_or_title
            """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), subSectorCode.trim().toUpperCase());
    }

    @Override
    public List<Client> findClientsBySector(String sectorCode) {
        var sql = """
            SELECT c.*
            FROM clients c
            JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code
            WHERE ss.sector_code = ?
            ORDER BY c.name_or_title
            """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), sectorCode.trim().toUpperCase());
    }

    @Override
    public List<Client> findClientsByDistrict(String districtCode) {
        var sql = """
            SELECT *
            FROM clients
            WHERE district_code = ?
            ORDER BY name_or_title
            """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), districtCode.trim().toUpperCase());
    }

    @Override
    public List<Client> searchClients(
            String socialRankCode,
            String clientTypeCode,
            String subSectorCode,
            String sectorCode,
            String districtCode,
            Boolean isBlocked) {

        StringBuilder sqlBuilder = new StringBuilder("SELECT c.* FROM clients c ");
        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        // Handle sector code (requires join)
        if (sectorCode != null) {
            sqlBuilder.append("JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code ");
            conditions.add("ss.sector_code = ?");
            params.add(sectorCode);
        }

        // Add WHERE clause if any conditions exist
        if (!conditions.isEmpty() || socialRankCode != null || clientTypeCode != null ||
                subSectorCode != null || districtCode != null || isBlocked != null) {
            sqlBuilder.append("WHERE ");

            // Add simple conditions
            if (socialRankCode != null) {
                conditions.add("c.social_rank_code = ?");
                params.add(socialRankCode.trim().toUpperCase());
            }
            if (clientTypeCode != null) {
                conditions.add("c.client_type_code = ?");
                params.add(clientTypeCode.trim().toUpperCase());
            }
            if (subSectorCode != null) {
                conditions.add("c.sub_sector_code = ?");
                params.add(subSectorCode.trim().toUpperCase());
            }
            if (districtCode != null) {
                conditions.add("c.district_code = ?");
                params.add(districtCode.trim().toUpperCase());
            }
            if (isBlocked != null) {
                conditions.add("c.is_blocked = ?");
                params.add(isBlocked);
            }

            sqlBuilder.append(String.join(" AND ", conditions));
        }

        sqlBuilder.append(" ORDER BY c.name_or_title");

        return jdbcTemplate.query(sqlBuilder.toString(),  new ClientRowMapper(), params.toArray());
    }

    @Override
    public long countAllClients() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM clients", Long.class);
    }

    @Override
    public long countActiveClients() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clients WHERE is_blocked = false", Long.class
        );
    }

    @Override
    public long countBlockedClients() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM clients WHERE is_blocked = true", Long.class
        );
    }

    @Override
    public long countClientsBySocialRank(String socialRankCode) {
        var sql = "SELECT COUNT(*) FROM clients WHERE social_rank_code = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, socialRankCode.trim().toUpperCase());
    }

    @Override
    public long countClientsByClientType(String clientTypeCode) {
        var sql = "SELECT COUNT(*) FROM clients WHERE client_type_code = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, clientTypeCode.trim().toUpperCase());
    }

    @Override
    public long countClientsBySector(String sectorCode) {
        var sql = """
            SELECT COUNT(*)
            FROM clients c
            JOIN sub_sectors ss ON c.sub_sector_code = ss.sub_sector_code
            WHERE ss.sector_code = ?
            """;
        return jdbcTemplate.queryForObject(sql, Long.class, sectorCode.trim().toUpperCase());
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

    private String validateAndGetSubSectorCode(String subSectorCode) {
        String normalizedCode = subSectorCode.trim().toUpperCase();

        // Check if sub-sector exists in the database
        var checkSql = "SELECT COUNT(*) FROM sub_sectors WHERE sub_sector_code = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, normalizedCode);

        if (count == null || count == 0) {
            throw new IllegalArgumentException(
                    "Invalid sub-sector code: '" + subSectorCode + "'. Code does not exist."
            );
        }

        return normalizedCode;
    }
}
