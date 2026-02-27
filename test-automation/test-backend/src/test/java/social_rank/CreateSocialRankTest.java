package social_rank;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.SocialRankDbAssertions;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.SocialRank;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateSocialRankTest extends BaseSocialRankTest {

    private final String RANK_CODE = GeneratorBuilder.generateTestCode();
    private final String RANK_NAME = GeneratorBuilder.generateString(10);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a social rank that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankAlreadyExistsTest() {
        SocialRank socialRankApi = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRankApi, SC_OK);
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        PostApiReqHelper.saveSocialRankAndValidate(socialRankApi, SC_SERVER_ERROR);
        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);
    }

    @ParameterizedTest(name = "[{index}] rankCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a social rank:
            1) with rank code = null,
            2) with rank code = empty string,
            3) with rank code = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankWithInvalidCodeTest(String rankCode) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank newSocialRankApi = SocialRank.builder()
                .rankCode(rankCode)
                .rankName(RANK_NAME)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(newSocialRankApi, SC_SERVER_ERROR);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);

        SocialRankDb newAddedSocialRankDb = SocialRankDbHelper.selectSocialRankByCode(rankCode);
        SocialRankDbAssertions.checkSocialRankPresence(newAddedSocialRankDb, false);
    }

    @ParameterizedTest(name = "[{index}] rankCode = {0}")
    @MethodSource("rankCodeProvider")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a social rank:
            1) with rank code that needs to be trimmed,
            2) with rank code that should be modified to upper case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankCodeTrimUppercaseTest(String rankCode) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        String socialRankCodeTrimmedUppercase = rankCode.trim().toUpperCase();

        SocialRank socialRankApi = SocialRank.builder()
                .rankCode(rankCode)
                .rankName(RANK_NAME)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRankApi, SC_OK);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(socialRankCodeTrimmedUppercase);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, true);
        SocialRankDbAssertions.checkSocialRankField("rankCode", socialRankDb.getRankCode(), socialRankCodeTrimmedUppercase);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld + 1);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a social rank with rank name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankNameAlreadyExistsTest() {
        SocialRankDb socialRankDb = SocialRankDb.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .build();
        int rowsCount = SocialRankDbHelper.insertSocialRank(socialRankDb);
        CommonDbAssertions.checkRowsInserted(rowsCount);

        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank newSocialRankApiSameName = SocialRank.builder()
                .rankCode(GeneratorBuilder.generateTestCode())
                .rankName(RANK_NAME)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(newSocialRankApiSameName, SC_SERVER_ERROR);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a social rank with rank name that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankNameTrimTest() {
        String rankNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String rankNameTrimmed = rankNameToTrim.trim();

        SocialRank socialRankApi = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(rankNameToTrim)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRankApi, SC_OK);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, true);
        SocialRankDbAssertions.checkSocialRankField("rankName", socialRankDb.getRankName(), rankNameTrimmed);
    }

    @ParameterizedTest(name = "[{index}] rankName = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a social rank:
            1) with rank name = null,
            2) with rank name = empty string,
            3) with rank name = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankWithInvalidNameTest(String rankName) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank newSocialRankApi = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(rankName)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(newSocialRankApi, SC_SERVER_ERROR);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, false);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a social rank and checks its default fields.")
    @Severity(SeverityLevel.CRITICAL)
    public void createSocialRankWithDefaultParametersTest() {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank newSocialRankApi = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(newSocialRankApi, SC_OK);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld + 1);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, true);
        SocialRankDbAssertions.checkDefaultPrivilegeLevel(socialRankDb.getPrivilegeLevel());
        SocialRankDbAssertions.checkSocialRankField("description", socialRankDb.getDescription(), "");
        SocialRankDbAssertions.checkSocialRankField("regulations", socialRankDb.getRegulations(), "");
    }


    private static Stream<Arguments> rankCodeProvider() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
