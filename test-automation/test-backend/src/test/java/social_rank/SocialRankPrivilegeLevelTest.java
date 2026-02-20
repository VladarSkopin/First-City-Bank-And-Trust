package social_rank;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.SocialRankDbAssertions;
import org.skopintsev.database.social_ranks.SocialRankDb;
import org.skopintsev.database.social_ranks.SocialRankDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.PrivilegeLevel;
import org.skopintsev.model.SocialRank;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

public class SocialRankPrivilegeLevelTest extends BaseSocialRankTest {

    private final String RANK_CODE = GeneratorBuilder.generateTestCode();
    private final String RANK_NAME = GeneratorBuilder.generateString(10);
    private static final String PRIVILEGE_LEVEL = PrivilegeLevel.HIGHEST.getText();

    @ParameterizedTest(name = "[{index}] privilegeLevel = {0}")
    @MethodSource("invalidPrivilegeLevelRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a social rank:
        1) with invalid privilege level,
        2) with privilege level = null,
        3) with privilege level = empty string.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void createSocialRankWithInvalidPrivilegeLevelTest(String privilegeLevel) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank socialRank = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .privilegeLevel(privilegeLevel)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRank, SC_SERVER_ERROR);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, false);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld);
    }

    @ParameterizedTest(name = "[{index}] privilegeLevel = {0}")
    @MethodSource("validPrivilegeLevelRequest")
    @Tag("regression")
    @Description("""
            Test uses API to post a social rank:
            1) with privilege level in lowercase,
            2) with privilege level in uppercase,
            3) with privilege level in mixed case.
            """)
    @Severity(SeverityLevel.NORMAL)
    public void createSocialRankWithValidPrivilegeLevelTest(String privilegeLevel) {
        int socialRanksCountOld = SocialRankDbHelper.getSocialRanksCount();

        SocialRank socialRank = SocialRank.builder()
                .rankCode(RANK_CODE)
                .rankName(RANK_NAME)
                .privilegeLevel(privilegeLevel)
                .build();
        PostApiReqHelper.saveSocialRankAndValidate(socialRank, SC_OK);

        SocialRankDb socialRankDb = SocialRankDbHelper.selectSocialRankByCode(RANK_CODE);
        SocialRankDbAssertions.checkSocialRankPresence(socialRankDb, true);
        SocialRankDbAssertions.checkSocialRankField("privilegeLevel", socialRankDb.getPrivilegeLevel(), PRIVILEGE_LEVEL);

        int socialRanksCountNew = SocialRankDbHelper.getSocialRanksCount();
        CommonDbAssertions.checkCounts(socialRanksCountNew, socialRanksCountOld + 1);
    }

    private static Stream<Arguments> invalidPrivilegeLevelRequest() {
        return Stream.of(
                Arguments.of(GeneratorBuilder.generateString(5)),
                Arguments.of(""),
                null
        );
    }

    private static Stream<Arguments> validPrivilegeLevelRequest() {
        return Stream.of(
                Arguments.of(PRIVILEGE_LEVEL.toLowerCase()),
                Arguments.of(PRIVILEGE_LEVEL),
                Arguments.of(PRIVILEGE_LEVEL.charAt(0) + PRIVILEGE_LEVEL.substring(1).toLowerCase())
        );
    }
}
