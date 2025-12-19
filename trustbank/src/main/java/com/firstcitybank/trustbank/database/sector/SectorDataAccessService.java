package com.firstcitybank.trustbank.database.sector;

import com.firstcitybank.trustbank.database.dao.SectorDao;
import com.firstcitybank.trustbank.model.Sector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SectorDataAccessService implements SectorDao {

    private final JdbcTemplate jdbcTemplate;

    public SectorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Sector> selectSectors() {
        var sql = """
                SELECT sector_code, sector_name, description
                FROM sectors
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new SectorRowMapper());
    }

    @Override
    public int insertSector(Sector sector) {
        var sql = """
            INSERT INTO sectors (sector_code, sector_name, description)
            VALUES (?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                sector.sectorCode().toUpperCase().trim(),
                sector.sectorName().trim(),
                sector.description().trim()
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String sectorName) {
        var sql = "SELECT COUNT(*) FROM sectors WHERE sector_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sectorName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String sectorCode) {
        var sql = "SELECT COUNT(*) FROM sectors WHERE sector_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sectorCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteSector(String sectorCode) {
        var sql = """
                DELETE FROM sectors
                WHERE sector_code = ?
                """;
        return jdbcTemplate.update(sql, sectorCode);
    }

    @Override
    public Optional<Sector> selectSectorByCode(String sectorCode) {
        var sql = """
                SELECT sector_code, sector_name, description
                FROM sectors
                WHERE sector_code = ?
                """;
        return jdbcTemplate.query(sql, new SectorRowMapper(), sectorCode)
                .stream()
                .findFirst();
    }
}
