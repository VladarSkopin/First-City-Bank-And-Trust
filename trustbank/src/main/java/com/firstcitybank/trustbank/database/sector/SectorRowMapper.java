package com.firstcitybank.trustbank.database.sector;

import com.firstcitybank.trustbank.model.sector.Sector;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorRowMapper implements RowMapper<Sector> {
    @Override
    public Sector mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Sector(
                rs.getString("sector_code"),
                rs.getString("sector_name"),
                rs.getString("description")
        );
    }
}
