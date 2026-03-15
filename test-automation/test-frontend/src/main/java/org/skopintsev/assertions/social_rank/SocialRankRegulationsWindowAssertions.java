package org.skopintsev.assertions.social_rank;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.social_ranks.SocialRankRegulationsWindow;

import static com.codeborne.selenide.Condition.text;

public class SocialRankRegulationsWindowAssertions {

    @Step("Check that regulations code is displayed with text '{0}'.")
    public static void checkRegulationsCodeText(String regulationsCode) {
        SocialRankRegulationsWindow.getRegulationCode().shouldBe(Condition.visible).shouldHave(text(regulationsCode));
    }

    @Step("Check that regulations level is displayed with text '{0}'.")
    public static void checkRegulationsLevelText(String levelText) {
        SocialRankRegulationsWindow.getRegulationLevel().shouldBe(Condition.visible).shouldHave(text(levelText));
    }

    @Step("Check that regulations description is displayed with text '{0}'.")
    public static void checkRegulationsDescriptionText(String levelText) {
        SocialRankRegulationsWindow.getRegulationDescription().shouldBe(Condition.visible).shouldHave(text(levelText));
    }

    @Step("Check that seal banner is displayed with text '{0}'.")
    public static void checkSealBannerText(String sealText) {
        SocialRankRegulationsWindow.getSealBanner().shouldBe(Condition.visible).shouldHave(text(sealText));
    }
}
