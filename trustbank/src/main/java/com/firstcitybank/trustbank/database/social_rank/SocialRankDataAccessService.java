package com.firstcitybank.trustbank.database.social_rank;

import com.firstcitybank.trustbank.database.dao.SocialRankDao;
import com.firstcitybank.trustbank.helper.Utils;
import com.firstcitybank.trustbank.model.SocialRank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SocialRankDataAccessService implements SocialRankDao {

    private final JdbcTemplate jdbcTemplate;

    public SocialRankDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SocialRank> selectSocialRanks() {
        var sql = """
                SELECT rank_code, rank_name, description, privilege_level, regulations
                FROM social_ranks
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new SocialRankRowMapper());
    }

    @Override
    public int insertSocialRank(SocialRank socialRank) {
        var sql = """
            INSERT INTO social_ranks (rank_code, rank_name, description, privilege_level, regulations)
            VALUES (?, ?, ?, ?, ?)
            """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                socialRank.rankCode().toUpperCase().trim(),
                socialRank.rankName().trim(),
                socialRank.description() != null ? socialRank.description().trim() : null,
                Utils.validateAndGetPrivilegeLevel(socialRank.privilegeLevel()),
                socialRank.regulations() != null ? socialRank.regulations().trim() : null
        );

        return rowsAffected;
    }

    @Override
    public boolean existsByName(String rankName) {
        var sql = "SELECT COUNT(*) FROM social_ranks WHERE rank_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rankName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCode(String rankCode) {
        var sql = "SELECT COUNT(*) FROM social_ranks WHERE rank_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, rankCode);
        return count != null && count > 0;
    }

    @Override
    public int deleteSocialRank(String rankCode) {
        var sql = """
                DELETE FROM social_ranks
                WHERE rank_code = ?
                """;
        return jdbcTemplate.update(sql, rankCode);
    }

    @Override
    public Optional<SocialRank> selectSocialRankByCode(String rankCode) {
        var sql = """
                SELECT rank_code, rank_name, description, privilege_level, regulations
                FROM social_ranks
                WHERE rank_code = ?
                """;
        return jdbcTemplate.query(sql, new SocialRankRowMapper(), rankCode)
                .stream()
                .findFirst();
    }
}
