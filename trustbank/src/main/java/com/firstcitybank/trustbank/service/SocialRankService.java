package com.firstcitybank.trustbank.service;

import com.firstcitybank.trustbank.database.dao.SocialRankDao;
import com.firstcitybank.trustbank.exception.NotFoundException;
import com.firstcitybank.trustbank.model.Currency;
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
        // todo
    }

    public void deleteSocialRank(String rankCode) {
        Optional<SocialRank> socialRanks = socialRankDao.selectSocialRankByCode(rankCode);
        socialRanks.ifPresentOrElse(socialRank -> {
            int result = socialRankDao.deleteSocialRank(rankCode);
            if (result != 1) {
                throw new IllegalStateException("Oops cannot delete Social rank");
            }
        }, () -> {
            throw new NotFoundException(String.format("Social rank with code %s not found", rankCode));
        });
    }
}
