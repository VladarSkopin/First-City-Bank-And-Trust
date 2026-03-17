package org.skopintsev.assertions.sector;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.sector.SectorDescriptionWindow;

public class SectorDescriptionWindowAssertions {

    @Step("Check sub-sector code is displayed with text '{0}'.")
    public static void checkSubSectorCodeText(String subSectorCode) {
        SectorDescriptionWindow.getSubSectorCodeWindow().shouldBe(Condition.visible).shouldHave(Condition.text(subSectorCode));
    }

    @Step("Check sub-sector name is displayed with text '{0}'.")
    public static void checkSectorName(String sectorName) {
        SectorDescriptionWindow.getSectorNameWindow().shouldBe(Condition.visible).shouldHave(Condition.text(sectorName));
    }

    @Step("Check sub-sector name is displayed with text '{0}'.")
    public static void checkSectorDescription(String description) {
        SectorDescriptionWindow.getSectorDescription().shouldBe(Condition.visible).shouldHave(Condition.text(description));
    }

}
