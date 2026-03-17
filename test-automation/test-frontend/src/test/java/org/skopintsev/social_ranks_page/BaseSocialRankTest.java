package org.skopintsev.social_ranks_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.SocialRank;
import org.skopintsev.models.api.factory.SocialRankFactory;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class BaseSocialRankTest extends BaseTest {

    List<SocialRank> BASE_SOCIAL_RANKS_LIST = SocialRankFactory.generateSocialRanksList();

    @BeforeEach
    public void openSocialRanksPage() {
        PostApiResponseHelper.stubGetSocialRanks(BASE_SOCIAL_RANKS_LIST);
        OpenUrl.openSocialRanksPage();
    }

}
