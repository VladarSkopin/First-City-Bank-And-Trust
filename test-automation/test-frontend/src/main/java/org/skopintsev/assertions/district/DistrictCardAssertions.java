package org.skopintsev.assertions.district;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.districts.DistrictCard;

public class DistrictCardAssertions {

    @Step("Check district code is displayed with text '{0}'.")
    public static void checkDistrictCode(String districtCode) {
        DistrictCard.getDistrictCode().shouldBe(Condition.visible).shouldHave(Condition.text(districtCode));
    }

    @Step("Check district name is displayed with text '{0}'.")
    public static void checkDistrictName(String districtCode) {
        DistrictCard.getDistrictName().shouldBe(Condition.visible).shouldHave(Condition.text(districtCode));
    }

    @Step("Check 'DETAILED MAP' button is enabled.")
    public static void checkDDetailedMapBtnEnabled() {
        DistrictCard.getDetailedMapBtn().shouldBe(Condition.visible).shouldBe(Condition.enabled);
    }

    @Step("Check 'DETAILED MAP' button has text '{0}'.")
    public static void checkDetailedMapBtnText(String buttonText) {
        DistrictCard.getDetailedMapBtn().shouldHave(Condition.text(buttonText));
    }

}
