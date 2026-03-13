package org.skopintsev.social_ranks_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.social_rank.SocialRanksPageAssertions;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class SocialRankInfoTest extends BaseSocialRankTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Social Ranks page information.")
    @Severity(SeverityLevel.NORMAL)
    public void socialRanksPageInfoTest() {

        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single social rank card information.")
    @Severity(SeverityLevel.NORMAL)
    public void socialRankCardInfoTest() {


    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Social Ranks page in case of empty response list.")
    @Severity(SeverityLevel.NORMAL)
    public void socialRanksEmptyResponseTest() {
        PostApiResponseHelper.stubGetSocialRanks(Collections.emptyList());
        Selenide.refresh();

        SocialRanksPageAssertions.checkPageTitleText("No Social Ranks Found");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Social Ranks page when no ranks were found.")
    @Severity(SeverityLevel.NORMAL)
    public void socialRanksNotFoundTest() {
        PostApiResponseHelper.stubGetSocialRanksNotFound(Collections.emptyList());
        Selenide.refresh();

        SocialRanksPageAssertions.checkPageTitleText("Failed to Load Social Ranks");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Social Ranks page in case of server error response.")
    @Severity(SeverityLevel.NORMAL)
    public void socialRanksServerErrorTest() {
        PostApiResponseHelper.stubGetSocialRanksServerError(Collections.emptyList());
        Selenide.refresh();

        SocialRanksPageAssertions.checkPageTitleText("Failed to Load Social Ranks");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

}
