package org.skopintsev.steps.common;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.common.HeaderPanel;

public class HeaderPanelSteps {

    @Step("Close notification panel.")
    public static void clickVaultsPageBtn() {
        HeaderPanel.getVaultsPageBtn().click();
    }

    @Step("Close notification panel.")
    public static void clickClientsPageBtn() {
        HeaderPanel.getClientsPageBtn().click();
    }

    @Step("Close notification panel.")
    public static void clickSocialRanksPageBtn() {
        HeaderPanel.getSocialRanksPageBtn().click();
    }

    @Step("Close notification panel.")
    public static void clickDistrictsPageBtn() {
        HeaderPanel.getDistrictsPageBtn().click();
    }

    @Step("Close notification panel.")
    public static void clickCurrenciesPageBtn() {
        HeaderPanel.getCurrenciesPageBtn().click();
    }

    @Step("Close notification panel.")
    public static void clickSectorsPageBtn() {
        HeaderPanel.getSectorsPageBtn().click();
    }

}
