package social_rank;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.SocialRankDbAssertions;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_NOT_FOUND;
import static org.skopintsev.constants.Constants.SC_OK;

public class DeleteSocialRankTest extends BaseSocialRankTest {

    @Test
    @Tag("regression")
    @Description("Test creates a new social rank in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSocialRankTest() {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        String rankCode = GeneratorBuilder.generateTestCode();
        SocialRankDb newSocialRankDb = SocialRankDb.builder()
                .rankCode(rankCode)
                .rankName(GeneratorBuilder.generateString(10))
                .build();
        int rowsInserted = SocialRankDbHelper.insertSocialRank(newSocialRankDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteSocialRankAndValidate(rankCode, SC_OK);

        newSocialRankDb = SocialRankDbHelper.selectSocialRankByCode(rankCode);
        SocialRankDbAssertions.checkSocialRankPresence(newSocialRankDb, false);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);
    }

    @ParameterizedTest(name = "[{index}] rankCode = {0}")
    @MethodSource("rankCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a social rank:
        1) with code = null,
        2) with code = empty string,
        3) a social rank that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteSocialRankNegativeTest(String rankCode) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        DeleteApiReqHelper.deleteSocialRankAndValidate(rankCode, SC_NOT_FOUND);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);
    }

    private static Stream<Arguments> rankCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }
}
