package com.firstcitybank.trustbank.database.district;

import com.firstcitybank.trustbank.model.District;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DistrictRowMapper implements RowMapper<District> {
    @Override
    public District mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new District(
                rs.getString("district_code"),
                rs.getString("district_name"));
    }
}
