package org.skopintsev.assertions.api;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.SocialRank;

import java.util.List;

public class SocialRankApiAssertions {

    @Step("Check that social ranks list is not null.")
    public static void checkNotNullSocialRanks(List<SocialRank> socialRanks) {
        Assertions.assertThat(socialRanks)
                .withFailMessage("Expected social ranks list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
