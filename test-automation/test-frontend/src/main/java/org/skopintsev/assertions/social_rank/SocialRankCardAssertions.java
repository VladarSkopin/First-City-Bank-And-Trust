package org.skopintsev.assertions.social_rank;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.social_ranks.SocialRankCard;

public class SocialRankCardAssertions {

    @Step("Check rank code is displayed with text '{0}'.")
    public static void checkSocialRankCodeText(String rankCode) {
        SocialRankCard.getRankCode().shouldBe(Condition.visible).shouldHave(Condition.text(rankCode));
    }

    @Step("Check rank name is displayed with text '{0}'.")
    public static void checkSocialRankNameText(String rankName) {
        SocialRankCard.getRankName().shouldBe(Condition.visible).shouldHave(Condition.text(rankName));
    }

    @Step("Check rank description is displayed with text '{0}'.")
    public static void checkRankDescriptionText(String descriptionText) {
        SocialRankCard.getRankDescription().shouldBe(Condition.visible).shouldHave(Condition.text(descriptionText));
    }

    @Step("Check privilege level label is displayed with text '{0}'.")
    public static void checkPrivilegeLabelText(String labelText) {
        SocialRankCard.getPrivilegeLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check privilege level value is displayed with text '{0}'.")
    public static void checkPrivilegeValueText(String levelText) {
        SocialRankCard.getPrivilegeValue().shouldBe(Condition.visible).shouldHave(Condition.text(levelText));
    }

    @Step("Check access rights label is displayed with text '{0}'.")
    public static void checkAccessRightsLabelText(String labelText) {
        SocialRankCard.getAccessRightsLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check access rights value is displayed with text '{0}'.")
    public static void checkAccessRightsValueText(String rightsText) {
        SocialRankCard.getAccessRightsValue().shouldBe(Condition.visible).shouldHave(Condition.text(rightsText));
    }

    @Step("Check 'VIEW REGULATIONS' button is enabled.")
    public static void checkViewRegulationsBtnEnabled() {
        SocialRankCard.getViewRegulationsBtn().shouldBe(Condition.visible).shouldBe(Condition.enabled);
    }

    @Step("Check 'VIEW REGULATIONS' button has text '{0}'.")
    public static void checkViewRegulationsBtnText(String buttonText) {
        SocialRankCard.getViewRegulationsBtn().shouldHave(Condition.text(buttonText));
    }

}
