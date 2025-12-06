package com.firstcitybank.trustbank.database.social_rank;

import com.firstcitybank.trustbank.model.SocialRank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SocialRankRowMapper implements RowMapper<SocialRank> {
    @Override
    public SocialRank mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SocialRank(
                rs.getString("rank_code"),
                rs.getString("rank_name"),
                rs.getString("description"),
                rs.getString("privilege_level"),
                rs.getString("regulations"));
    }
}
