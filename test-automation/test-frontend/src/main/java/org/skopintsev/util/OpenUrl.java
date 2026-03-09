package org.skopintsev.util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.skopintsev.steps.common.elements.LoadingSpinnerSteps;

import static org.skopintsev.constants.Constants.*;

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
        LoadingSpinnerSteps.waitingForPageLoading();
    }

    @Step("Open districts page.")
    public static void openDistrictsPage() {
        Selenide.open(DISTRICTS_PAGE_URL);
        LoadingSpinnerSteps.waitingForPageLoading();
    }
}
