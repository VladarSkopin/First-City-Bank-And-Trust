package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SocialRankDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.SocialRank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialRankService {

    private final SocialRankDao socialRankDao;

    public SocialRankService(SocialRankDao socialRankDao) {
        this.socialRankDao = socialRankDao;
    }

    public List<SocialRank> getSocialRanks() {
        return socialRankDao.selectSocialRanks();
    }

    public void addNewSocialRank(SocialRank socialRank) {
        // 1. Validate input
        if (socialRank == null) {
            throw new IllegalArgumentException("Social Rank data cannot be null");
        }

        if (socialRank.rankCode() == null || socialRank.rankCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Rank code is required");
        }

        if (socialRank.rankName() == null || socialRank.rankName().trim().isEmpty()) {
            throw new IllegalArgumentException("Social Rank name is required");
        }

        // 2. Check if social rank exists
        boolean rankExists = socialRankDao.existsByName(socialRank.rankName());
        if (rankExists) {
            throw new IllegalStateException("Social Rank with name '" + socialRank.rankName() + "' already exists");
        }

        // 3. Insert new social rank
        Integer rowsAffected = socialRankDao.insertSocialRank(socialRank);

        // 4. Check if insertion was successful
        if (rowsAffected == null || rowsAffected <= 0) {
            throw new IllegalStateException("Failed to insert Social Rank");
        }
    }

    public void deleteSocialRank(String rankCode) {
        if (rankCode == null || rankCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Rank code cannot be null or empty");
        }

        String normalizedCode = rankCode.trim().toUpperCase();

        Optional<SocialRank> socialRanks = socialRankDao.selectSocialRankByCode(normalizedCode);
        socialRanks.ifPresentOrElse(socialRank -> {
            int result = socialRankDao.deleteSocialRank(normalizedCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Social Rank");
            }
        }, () -> {
            throw new NotFoundException(String.format("Social Rank with code %s not found", normalizedCode));
        });
    }
}
