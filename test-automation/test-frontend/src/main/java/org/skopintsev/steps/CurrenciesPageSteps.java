package org.skopintsev.steps;

import io.qameta.allure.Step;
import org.skopintsev.models.gui.CurrenciesPage;
import org.skopintsev.util.helpers.ElementHelper;

public class CurrenciesPageSteps {

    @Step("Open main page of the First City Bank & Trust in Chrome.")
    public static void waitingForCurrenciesPageLoading() {
        ElementHelper.waitingForLoading4Sec(CurrenciesPage.getCurrenciesPageLoader());
    }
}
