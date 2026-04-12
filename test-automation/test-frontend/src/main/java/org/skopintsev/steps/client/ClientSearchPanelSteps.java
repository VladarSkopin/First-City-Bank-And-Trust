package org.skopintsev.steps.client;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.clients.ClientSearchPanel;

public class ClientSearchPanelSteps {

    @Step("Input client name = '{0}'.")
    public static void typeClientName(String searchText) {
        ClientSearchPanel.getInputTextClientName().type(searchText);
    }

    @Step("Click select social ranks.")
    public static void clickSocialRanksSelect() {
        ClientSearchPanel.getSelectRank().click();
    }

    // todo: click social rank option


    @Step("Click select client types.")
    public static void clickClientTypesSelect() {
        ClientSearchPanel.getSelectType().click();
    }

    // todo: click client type option


    @Step("Click select sub-sectors.")
    public static void clickSubSectorsSelect() {
        ClientSearchPanel.getSelectSubSector().click();
    }

    // todo: click sub-sector option


    @Step("Click select districts.")
    public static void clickDistrictsSelect() {
        ClientSearchPanel.getSelectDistrict().click();
    }

    // todo: click district option


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
