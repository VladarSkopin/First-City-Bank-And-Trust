package org.skopintsev.assertions.sector;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.sector.SectorsPage;

public class SectorsPageAssertions {

    @Step("Check 'Sectors' page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        SectorsPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check sub-sectors count label is displayed with text '{0}'.")
    public static void checkSubSectorsCountLabelText(String countLabel) {
        SectorsPage.getCountLabelSubSectors().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check sub-sectors count value is displayed with text '{0}'.")
    public static void checkSubSectorsCountValueText(int countValue) {
        SectorsPage.getCountValueSubSectors().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

    @Step("Check sectors count label is displayed with text '{0}'.")
    public static void checkSectorsCountLabelText(String countLabel) {
        SectorsPage.getCountLabelSectors().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check sectors count value is displayed with text '{0}'.")
    public static void checkSectorsCountValueText(int countValue) {
        SectorsPage.getCountValueSectors().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

}
