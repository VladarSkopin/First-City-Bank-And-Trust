package org.skopintsev.assertions.client;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.clients.ClientSearchPanel;

public class ClientsSearchAssertions {

    @Step("Check input client name is displayed with text '{0}'.")
    public static void checkInputClientNameText(String text) {
        ClientSearchPanel.getInputTextClientName().shouldBe(Condition.visible).shouldHave(Condition.value(text));
    }

    @Step("Check social ranks selection is displayed with text '{0}'.")
    public static void checkSocialRanksSelectionText(String text) {
        ClientSearchPanel.getSelectRank().shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    @Step("Check client types selection is displayed with text '{0}'.")
    public static void checkClientTypesSelectionText(String text) {
        ClientSearchPanel.getSelectType().shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    @Step("Check sub-sectors selection is displayed with text '{0}'.")
    public static void checkSubSectorsSelectionText(String text) {
        ClientSearchPanel.getSelectSubSector().shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    @Step("Check district selection is displayed with text '{0}'.")
    public static void checkDistrictSelectionText(String text) {
        ClientSearchPanel.getSelectDistrict().shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    @Step("Check isBlocked label is displayed with text '{0}'.")
    public static void checkIsBlockedLabelText(String text) {
        ClientSearchPanel.getIsBlockedLabel().shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    @Step("Check isBlocked checkbox is selected = '{0}'.")
    public static void checkIsBlockedCheckboxChecked(boolean shouldBeSelected) {
        ClientSearchPanel.getInputCheckboxIsBlocked().should(
                shouldBeSelected ? Condition.checked : Condition.not(Condition.checked));
    }

    @Step("Check 'SEARCH CLIENTS' is displayed with text '{0}'.")
    public static void checkSearchBtnText(String btnText) {
        ClientSearchPanel.getBtnSearch().shouldBe(Condition.visible).shouldHave(Condition.text(btnText));
    }

    @Step("Check 'SEARCH CLIENTS' button should be enabled = '{0}'.")
    public static void checkSearchBtnEnabled(boolean shouldBeEnabled) {
        ClientSearchPanel.getBtnSearch().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

    @Step("Check 'RESET' is displayed with text '{0}'.")
    public static void checkResetBtnText(String btnText) {
        ClientSearchPanel.getBtnReset().shouldBe(Condition.visible).shouldHave(Condition.text(btnText));
    }

    @Step("Check 'RESET' button should be enabled = '{0}'.")
    public static void checkResetBtnEnabled(boolean shouldBeEnabled) {
        ClientSearchPanel.getBtnReset().shouldBe(shouldBeEnabled ? Condition.enabled : Condition.disabled);
    }

}
