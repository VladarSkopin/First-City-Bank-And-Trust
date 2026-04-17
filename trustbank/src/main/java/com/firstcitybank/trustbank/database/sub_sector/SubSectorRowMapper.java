package com.firstcitybank.trustbank.database.sub_sector;

import com.firstcitybank.trustbank.model.sector.SubSector;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubSectorRowMapper  implements RowMapper<SubSector> {
    @Override
    public SubSector mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SubSector(
                rs.getString("sub_sector_code"),
                rs.getString("sub_sector_name"),
                rs.getString("description"),
                rs.getString("sector_code")
        );
    }
}