package org.skopintsev.social_ranks_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.common.windows.ModalWindowAssertions;
import org.skopintsev.assertions.social_rank.SocialRankRegulationsWindowAssertions;
import org.skopintsev.models.api.SocialRank;
import org.skopintsev.steps.social_rank.SocialRankCardSteps;

public class SocialRankRegulationsTest extends BaseSocialRankTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of social rank regulations.")
    @Severity(SeverityLevel.NORMAL)
    public void regulationsModalWindowTest() {
        SocialRankCardSteps.clickViewRegulationsBtn();

        SocialRank socialRank = BASE_SOCIAL_RANKS_LIST.get(0);

        ModalWindowAssertions.checkHeaderText(socialRank.getRankName() + " Regulations");
        ModalWindowAssertions.checkModalBodyMinHeight(400);
        ModalWindowAssertions.checkModalBodyMinWidth(600);
        SocialRankRegulationsWindowAssertions.checkRegulationsCodeText("CODE: " + socialRank.getRankCode());
        SocialRankRegulationsWindowAssertions.checkRegulationsLevelText(
                "PRIVILEGE LEVEL: " + socialRank.getPrivilegeLevel());
        SocialRankRegulationsWindowAssertions.checkRegulationsDescriptionText(socialRank.getRegulations());
        SocialRankRegulationsWindowAssertions.checkSealBannerText("APPROVED BY CITY COUNCIL");
        ButtonElementAssertions.checkOkBtnIsVisible();
        ButtonElementAssertions.checkOkBtnEnabled(true);
        ButtonElementAssertions.checkCrossCloseBtnIsVisible();
        ButtonElementAssertions.checkCrossCloseBtnEnabled(true);
    }

}
