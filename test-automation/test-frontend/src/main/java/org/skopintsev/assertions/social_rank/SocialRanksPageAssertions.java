package org.skopintsev.assertions.social_rank;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.social_ranks.SocialRanksPage;

public class SocialRanksPageAssertions {

    @Step("Check 'Social Ranks' page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        SocialRanksPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check social ranks count label is displayed with text '{0}'.")
    public static void checkSocialRanksCountLabelText(String countLabel) {
        SocialRanksPage.getCountLabel().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check social ranks count value is displayed with text '{0}'.")
    public static void checkSocialRanksCountValueText(int count) {
        SocialRanksPage.getCountValue().shouldBe(Condition.visible)
                .shouldHave(Condition.text(String.valueOf(count)));
    }

}
