package social_rank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;

public class BaseSocialRankTest {
    @BeforeEach
    public void setUp() {
        SocialRankDbHelper.deleteAllTestSocialRanks();
    }

    @AfterEach
    public void tearDown() {
        SocialRankDbHelper.deleteAllTestSocialRanks();
    }
}
