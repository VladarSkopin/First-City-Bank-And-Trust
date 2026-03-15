package org.skopintsev.steps.social_rank;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.social_ranks.SocialRankCard;

public class SocialRankCardSteps {

    @Step("Click 'VIEW REGULATIONS' button.")
    public static void clickViewRegulationsBtn() {
        SocialRankCard.getViewRegulationsBtn().click();
    }

}
