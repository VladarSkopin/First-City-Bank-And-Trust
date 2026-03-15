package org.skopintsev.assertions.district;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.districts.DistrictMapWindow;

import static com.codeborne.selenide.Condition.not;

public class DistrictMapWindowAssertions {

    @Step("Check that district map is visible.")
    public static void checkDistrictMapImageIsVisible(boolean shouldBeVisible) {
        DistrictMapWindow.getDistrictMapImg().shouldBe(shouldBeVisible ? Condition.visible : not(Condition.visible));
    }

    @Step("Check that district map banner is displayed with text '{0}'.")
    public static void checkDistrictMapBannerText(String bannerText) {
        DistrictMapWindow.getMapBanner().shouldBe(Condition.visible).shouldHave(Condition.text(bannerText));
    }

    @Step("Check that district map description is displayed with text '{0}'.")
    public static void checkDistrictMapDescriptionText(String descriptionText) {
        DistrictMapWindow.getMapDescription().shouldBe(Condition.visible).shouldHave(Condition.text(descriptionText));
    }

}
