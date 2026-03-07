package org.skopintsev.steps.common;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.HeaderPanel;

public class HeaderPanelSteps {

    @Step("Click 'VAULT' tab.")
    public static void clickVaultsPageBtn() {
        HeaderPanel.getVaultsPageBtn().click();
    }

    @Step("Click 'CLIENTS' tab.")
    public static void clickClientsPageBtn() {
        HeaderPanel.getClientsPageBtn().click();
    }

    @Step("Click 'SOCIAL RANKS' tab.")
    public static void clickSocialRanksPageBtn() {
        HeaderPanel.getSocialRanksPageBtn().click();
    }

    @Step("Click 'DISTRICTS' tab.")
    public static void clickDistrictsPageBtn() {
        HeaderPanel.getDistrictsPageBtn().click();
    }

    @Step("Click 'CURRENCIES' tab.")
    public static void clickCurrenciesPageBtn() {
        HeaderPanel.getCurrenciesPageBtn().click();
    }

    @Step("Click 'SECTORS' tab.")
    public static void clickSectorsPageBtn() {
        HeaderPanel.getSectorsPageBtn().click();
    }

}
