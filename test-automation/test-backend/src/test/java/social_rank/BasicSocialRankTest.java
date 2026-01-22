package social_rank;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.SocialRankApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.SocialRankDbAssertions;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.PrivilegeLevel;
import org.skopintsev.model.SocialRank;
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicSocialRankTest extends BaseSocialRankTest {

    private final String RANK_CODE = GeneratorBuilder.generateTestCode();
    private final String RANK_NAME = GeneratorBuilder.generateString(10);
    private final String PRIVILEGE_LEVEL = PrivilegeLevel.RESTRICTED.getText();
    private final String DESCRIPTION = GeneratorBuilder.generateString(1000);
    private final String REGULATIONS = GeneratorBuilder.generateString(1000);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Social Rank object into the Database and checks API for the new added social rank.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSocialRankDbTest() {
        SocialRankDb socialRankDb = SocialRankDb.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .description(DESCRIPTION)
                .privilegeLevel(PRIVILEGE_LEVEL)
                .regulations(REGULATIONS)
                .build();
        int rowsInserted = SocialRankDbHelper.insertSocialRank(socialRankDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<SocialRank> ranks = GetApiReqHelper.getSocialRanksAndValidate(SC_OK);
        SocialRankApiAssertions.checkNotNullSocialRanks(ranks);

        SocialRank newAddedSocialRankApi = ranks
                .stream()
                .filter(s -> s.getRankCode().equals(RANK_CODE))
                .findFirst()
                .orElse(null);
        SocialRankDbAssertions.checkSocialRankField("rankName", newAddedSocialRankApi.getRankName(), RANK_NAME);
        SocialRankDbAssertions.checkSocialRankField("privilegeLevel", newAddedSocialRankApi.getPrivilegeLevel(),
                PRIVILEGE_LEVEL);
        SocialRankDbAssertions.checkSocialRankField("description", newAddedSocialRankApi.getDescription(),
                DESCRIPTION);
        SocialRankDbAssertions.checkSocialRankField("regulations", newAddedSocialRankApi.getRegulations(),
                REGULATIONS);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Social Rank object and checks Database for the new added social rank.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSocialRankApiTest() {
        SocialRank socialRank = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .description(DESCRIPTION)
                .privilegeLevel(PRIVILEGE_LEVEL)
                .regulations(REGULATIONS)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRank, SC_OK);

        List<SocialRank> ranks = GetApiReqHelper.getSocialRanksAndValidate(SC_OK);
        SocialRankApiAssertions.checkNotNullSocialRanks(ranks);

        SocialRankDb newAddedSocialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(newAddedSocialRankDb, true);
        SocialRankDbAssertions.checkSocialRankField("rankName", newAddedSocialRankDb.getRankName(),
                RANK_NAME);
        SocialRankDbAssertions.checkSocialRankField("privilegeLevel", newAddedSocialRankDb.getPrivilegeLevel(),
                PRIVILEGE_LEVEL);
        SocialRankDbAssertions.checkSocialRankField("description", newAddedSocialRankDb.getDescription(),
                DESCRIPTION);
        SocialRankDbAssertions.checkSocialRankField("regulations", newAddedSocialRankDb.getRegulations(),
                REGULATIONS);
    }
}
