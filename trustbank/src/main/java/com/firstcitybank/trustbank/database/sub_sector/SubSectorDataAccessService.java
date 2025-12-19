package com.firstcitybank.trustbank.database.sub_sector;

import com.firstcitybank.trustbank.database.dao.SubSectorDao;
import com.firstcitybank.trustbank.model.SubSector;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubSectorDataAccessService implements SubSectorDao {

    private final JdbcTemplate jdbcTemplate;

    public SubSectorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<SubSector> selectSubSectors() {
        var sql = """
                SELECT sub_sector_code, sub_sector_name, description, sector_code
                FROM sub_sectors
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new SubSectorRowMapper());
    }

    @Override
    public int insertSubSector(SubSector subSector) {
        return 0;
    }

    @Override
    public boolean existsByName(String subSectorName) {
        return false;
    }

    @Override
    public boolean existsByCode(String subSectorCode) {
        return false;
    }

    @Override
    public int deleteSubSector(String subSectorCode) {
        return 0;
    }

    @Override
    public Optional<SubSector> selectSubSectorByCode(String subSectorCode) {
        return Optional.empty();
    }
}
