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
        return List.of();
    }

    @Override
    public int insertSector(Sector sector) {
        return 0;
    }

    @Override
    public boolean existsByName(String sectorName) {
        return false;
    }

    @Override
    public boolean existsByCode(String sectorCode) {
        return false;
    }

    @Override
    public int deleteSector(String sectorCode) {
        return 0;
    }

    @Override
    public Optional<Sector> selectSectorCode(String sectorCode) {
        return Optional.empty();
    }
}
