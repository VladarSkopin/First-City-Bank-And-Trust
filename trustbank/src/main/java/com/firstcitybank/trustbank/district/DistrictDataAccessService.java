package com.firstcitybank.trustbank.district;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DistrictDataAccessService implements DistrictDao {

    private final JdbcTemplate jdbcTemplate;

    public DistrictDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<District> selectDistricts() {
        var sql = """
                SELECT district_code, district_name
                FROM districts
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new DistrictRowMapper());
    }

    @Override
    public int insertDistrict(District district) {
        return 0;
    }

    @Override
    public boolean existsByName(String districtName) {
        return false;
    }

    @Override
    public boolean existsByCode(String districtCode) {
        return false;
    }

    @Override
    public int deleteDistrict(String districtCode) {
        return 0;
    }

    @Override
    public Optional<District> selectDistrictByCode(String districtCode) {
        return Optional.empty();
    }
}
