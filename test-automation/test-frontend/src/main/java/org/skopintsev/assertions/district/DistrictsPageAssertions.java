package org.skopintsev.assertions.district;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.districts.DistrictsPage;

public class DistrictsPageAssertions {

    @Step("Check 'Districts' page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        DistrictsPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check districts count label is displayed with text '{0}'.")
    public static void checkDistrictsCountLabel(String countLabel) {
        DistrictsPage.getDistrictsCountLabel().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check districts count value is displayed with text '{0}'.")
    public static void checkDistrictsCountValue(int count) {
        DistrictsPage.getDistrictsCountValue().shouldBe(Condition.visible)
                .shouldHave(Condition.text(String.valueOf(count)));
    }
}
