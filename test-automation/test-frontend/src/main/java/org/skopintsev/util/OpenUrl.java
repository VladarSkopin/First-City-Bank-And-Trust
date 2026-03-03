package org.skopintsev.util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;

import static org.skopintsev.constants.Constants.CURRENCIES_PAGE_URL;
import static org.skopintsev.constants.Constants.FIRST_CITY_BANK_REACT_URL;

public class OpenUrl {

    @Step("Open main page of the First City Bank & Trust in Chrome.")
    public static void openFirstCityBank() {
        Configuration.browser = "chrome";
        Selenide.open(FIRST_CITY_BANK_REACT_URL);
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @Step("Open currencies page.")
    public static void openCurrenciesPage() {
        Selenide.open(CURRENCIES_PAGE_URL);
        // todo: CurrenciesPageSteps.waitingForCurrenciesPageLoading();
    }
}
