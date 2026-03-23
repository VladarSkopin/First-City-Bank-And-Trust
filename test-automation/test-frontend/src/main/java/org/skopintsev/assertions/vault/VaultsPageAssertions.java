package org.skopintsev.assertions.vault;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.vaults.VaultsPage;

public class VaultsPageAssertions {

    @Step("Check 'Vaults' page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        VaultsPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check active vaults count label is displayed with text '{0}'.")
    public static void checkActiveVaultsCountLabelText(String countLabel) {
        VaultsPage.getCountLabelVaults().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check total clients count value is displayed with text '{0}'.")
    public static void checkActiveVaultsCountValueText(long countValue) {
        VaultsPage.getCountValueVaults().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }

    @Step("Check currencies count label is displayed with text '{0}'.")
    public static void checkCurrenciesCountLabelText(String countLabel) {
        VaultsPage.getCountLabelCurrencies().shouldBe(Condition.visible).shouldHave(Condition.text(countLabel));
    }

    @Step("Check currencies count value is displayed with text '{0}'.")
    public static void checkCurrenciesCountValueText(long countValue) {
        VaultsPage.getCountValueCurrencies().shouldBe(Condition.visible).shouldHave(
                Condition.text(String.valueOf(countValue)));
    }
}
