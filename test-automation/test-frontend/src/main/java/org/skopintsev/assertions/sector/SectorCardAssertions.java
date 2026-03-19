package org.skopintsev.assertions.sector;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.sectors.SectorCard;

public class SectorCardAssertions {

    @Step("Check sub-sector name is displayed with text '{0}'.")
    public static void checkSubSectorName(String subSectorName) {
        SectorCard.getSubSectorName().shouldBe(Condition.visible).shouldHave(Condition.text(subSectorName));
    }

    @Step("Check sub-sector code label is displayed with text '{0}'.")
    public static void checkSubSectorCodeLabelText(String labelText) {
        SectorCard.getSubSectorCodeLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check sub-sector code value is displayed with text '{0}'.")
    public static void checkSubSectorCodeValueText(String valueText) {
        SectorCard.getSubSectorCodeValue().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check sector name label is displayed with text '{0}'.")
    public static void checkSectorNameLabelText(String labelText) {
        SectorCard.getSectorNameLabel().shouldBe(Condition.visible).shouldHave(Condition.text(labelText));
    }

    @Step("Check sector name value is displayed with text '{0}'.")
    public static void checkSectorNameValueText(String valueText) {
        SectorCard.getSectorNameValue().shouldBe(Condition.visible).shouldHave(Condition.text(valueText));
    }

    @Step("Check 'INFO' button has text '{0}'.")
    public static void checkInfoBtnText(String buttonText) {
        SectorCard.getViewDescriptionBtn().shouldBe(Condition.visible).shouldHave(Condition.text(buttonText));
    }
}
