package com.firstcitybank.trustbank.database.district;

import com.firstcitybank.trustbank.database.dao.DistrictDao;
import com.firstcitybank.trustbank.model.District;
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
        var sql = """
            INSERT INTO districts (district_code, district_name)
            VALUES (?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                district.districtCode().toUpperCase().trim(),
                district.districtName().trim()
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String districtName) {
        var sql = "SELECT COUNT(*) FROM districts WHERE district_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, districtName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String districtCode) {
        var sql = "SELECT COUNT(*) FROM districts WHERE district_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, districtCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteDistrict(String districtCode) {
        var sql = """
                DELETE FROM districts
                WHERE district_code = ?
                """;
        return jdbcTemplate.update(sql, districtCode);
    }

    @Override
    public Optional<District> selectDistrictByCode(String districtCode) {
        var sql = """
                SELECT district_code, district_name
                FROM districts
                WHERE district_code = ?
                """;
        return jdbcTemplate.query(sql, new DistrictRowMapper(), districtCode)
                .stream()
                .findFirst();
    }
}
