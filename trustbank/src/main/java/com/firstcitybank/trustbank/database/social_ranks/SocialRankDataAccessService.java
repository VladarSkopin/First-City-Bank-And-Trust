package com.firstcitybank.trustbank.database.social_ranks;

import com.firstcitybank.trustbank.database.dao.SocialRankDao;
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
        return 0;
    }

    @Override
    public boolean existsByName(String rankName) {
        return false;
    }

    @Override
    public boolean existsByCode(String rankCode) {
        return false;
    }

    @Override
    public int deleteSocialRank(String rankCode) {
        return 0;
    }

    @Override
    public Optional<SocialRank> selectSocialRankByCode(String rankCode) {
        return Optional.empty();
    }
}
