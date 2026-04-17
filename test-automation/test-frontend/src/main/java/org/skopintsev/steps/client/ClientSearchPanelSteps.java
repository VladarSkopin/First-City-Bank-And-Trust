package org.skopintsev.steps.client;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.clients.ClientSearchPanel;

public class ClientSearchPanelSteps {

    @Step("Type client name = '{0}'.")
    public static void typeClientName(String searchText) {
        ClientSearchPanel.getInputTextClientName().type(searchText);
    }

    @Step("Select social rank option by visible text = '{0}'.")
    public static void selectOptionSocialRankByVisibleText(String optionText) {
        ClientSearchPanel.getSelectRank().selectOption(optionText);
    }

    @Step("Select social rank option by value = '{0}'.")
    public static void selectOptionSocialRankByValue(String optionValue) {
        ClientSearchPanel.getSelectRank().selectOptionByValue(optionValue);
    }

    @Step("Select client type option by visible text = '{0}'.")
    public static void selectOptionClientTypeByVisibleText(String optionText) {
        ClientSearchPanel.getSelectType().selectOption(optionText);
    }

    @Step("Select client type option by value = '{0}'.")
    public static void selectOptionClientTypeByValue(String optionValue) {
        ClientSearchPanel.getSelectType().selectOptionByValue(optionValue);
    }

    @Step("Select sub-sector option by visible text = '{0}'.")
    public static void selectOptionSubSectorByVisibleText(String optionText) {
        ClientSearchPanel.getSelectSubSector().selectOption(optionText);
    }

    @Step("Select sub-sector option by value = '{0}'.")
    public static void selectOptionSubSectorByValue(String optionValue) {
        ClientSearchPanel.getSelectSubSector().selectOptionByValue(optionValue);
    }

    @Step("Select district option by visible text = '{0}'.")
    public static void selectOptionDistrictByVisibleText(String optionText) {
        ClientSearchPanel.getSelectDistrict().selectOption(optionText);
    }

    @Step("Select district option by value = '{0}'.")
    public static void selectOptionDistrictByValue(String optionValue) {
        ClientSearchPanel.getSelectDistrict().selectOptionByValue(optionValue);
    }

    @Step("Click 'isBlocked' checkbox.")
    public static void clickIsBlockedCheckbox() {
        ClientSearchPanel.getInputCheckboxIsBlocked().click();
    }

    @Step("Click 'SEARCH CLIENTS' button.")
    public static void clickSearchClientsBtn() {
        ClientSearchPanel.getBtnSearch().click();
    }

    @Step("Click 'RESET' button.")
    public static void clickResetBtn() {
        ClientSearchPanel.getBtnReset().click();
    }
}
