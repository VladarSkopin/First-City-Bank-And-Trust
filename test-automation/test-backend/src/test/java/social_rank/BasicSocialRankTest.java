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

    private final String rankCode = GeneratorBuilder.generateTestCode();
    private final String rankName = GeneratorBuilder.generateString(10);
    private final String defaultPrivilegeLevel = PrivilegeLevel.STANDARD.getText();

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Social Rank object into the Database and checks API for the new added social rank.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSocialRankDbTest() {
        SocialRankDb socialRankDb = SocialRankDb.builder()
                .rankCode(rankCode)
                .rankName(rankName)
                .build();
        int rowsInserted = SocialRankDbHelper.insertSocialRank(socialRankDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<SocialRank> ranks = GetApiReqHelper.getSocialRanksAndValidate(SC_OK);
        SocialRankApiAssertions.checkNotNullSocialRanks(ranks);

        SocialRank newAddedSocialRankApi = ranks
                .stream()
                .filter(s -> s.getRankCode().equals(rankCode))
                .findFirst()
                .orElse(null);
        SocialRankDbAssertions.checkSocialRankField("rankName", newAddedSocialRankApi.getRankName(), rankName);
        SocialRankDbAssertions.checkSocialRankField("privilegeLevel", newAddedSocialRankApi.getPrivilegeLevel(),
                defaultPrivilegeLevel);
        SocialRankDbAssertions.checkSocialRankField("description", newAddedSocialRankApi.getDescription(),
                "");
        SocialRankDbAssertions.checkSocialRankField("regulations", newAddedSocialRankApi.getRegulations(),
                "");
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Social Rank object and checks Database for the new added social rank.")
    @Severity(SeverityLevel.BLOCKER)
    public void createSocialRankApiTest() {
        SocialRank socialRank = SocialRank.builder()
                .rankCode(rankCode)
                .rankName(rankName)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRank, SC_OK);

        List<SocialRank> ranks = GetApiReqHelper.getSocialRanksAndValidate(SC_OK);
        SocialRankApiAssertions.checkNotNullSocialRanks(ranks);

        SocialRankDb newAddedSocialRankDb = SocialRankDbHelper.selectSocialRankByCode(rankCode);
        SocialRankDbAssertions.checkSocialRankPresence(newAddedSocialRankDb, true);
        SocialRankDbAssertions.checkSocialRankField("rankName", newAddedSocialRankDb.getRankName(),
                rankName);
        SocialRankDbAssertions.checkSocialRankField("privilegeLevel", newAddedSocialRankDb.getPrivilegeLevel(),
                defaultPrivilegeLevel);
        SocialRankDbAssertions.checkSocialRankField("description", newAddedSocialRankDb.getDescription(),
                "");
        SocialRankDbAssertions.checkSocialRankField("regulations", newAddedSocialRankDb.getRegulations(),
                "");
    }
}
