package com.firstcitybank.trustbank.database.dao;

import com.firstcitybank.trustbank.model.SocialRank;

import java.util.List;
import java.util.Optional;

public interface SocialRankDao {
    List<SocialRank> selectSocialRanks();
    int insertSocialRank(SocialRank socialRank);
    boolean existsByName(String rankName);
    boolean existsByCode(String rankCode);
    int deleteSocialRank(String rankCode);
    Optional<SocialRank> selectSocialRankByCode(String rankCode);
}
