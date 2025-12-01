package com.firstcitybank.trustbank.database.social_ranks;

import com.firstcitybank.trustbank.database.dao.SocialRankDao;
import com.firstcitybank.trustbank.model.SocialRank;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SocialRankDataAccessService implements SocialRankDao {

    @Override
    public List<SocialRank> selectSocialRanks() {
        return List.of();
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
