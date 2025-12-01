package com.firstcitybank.trustbank.controller;

import com.firstcitybank.trustbank.model.SocialRank;
import com.firstcitybank.trustbank.service.SocialRankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.firstcitybank.trustbank.helper.Paths.CODE_PATH;
import static com.firstcitybank.trustbank.helper.Paths.SOCIAL_RANKS_PATH;

@RestController
@RequestMapping(path = SOCIAL_RANKS_PATH)
public class SocialRankController {

    private final SocialRankService socialRankService;

    public SocialRankController(SocialRankService socialRankService) {
        this.socialRankService = socialRankService;
    }

    @GetMapping
    public List<SocialRank> getSocialRanks() {
        return socialRankService.getSocialRanks();
    }

    @PostMapping
    public void addSocialRank(@RequestBody SocialRank socialRank) {
        socialRankService.addNewSocialRank(socialRank);
    }

    @DeleteMapping(CODE_PATH)
    public void deleteSocialRank(@PathVariable("code") String code) {
        socialRankService.deleteSocialRank(code);
    }
}
