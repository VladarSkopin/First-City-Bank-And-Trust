package org.skopintsev.assertions.currency;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.currencies.CurrenciesPage;

public class CurrenciesPageAssertions {

    @Step("Check page title is displayed with text '{0}'.")
    public static void checkPageTitleText(String titleText) {
        CurrenciesPage.getPageTitle().shouldBe(Condition.visible).shouldHave(Condition.text(titleText));
    }

    @Step("Check exchange rate title is displayed with text '{0}'.")
    public static void checkExchangeRateTitleText(String exchangeRateTitleText) {
        CurrenciesPage.getExchangeRateTitle().shouldBe(Condition.visible)
                .shouldHave(Condition.text(exchangeRateTitleText));
    }

    @Step("Check exchange rate label is displayed with text '{0}'.")
    public static void checkExchangeRateLabelText(String exchangeRateLabelText) {
        CurrenciesPage.getExchangeRateLabel().shouldBe(Condition.visible)
                .shouldHave(Condition.text(exchangeRateLabelText));
    }

    @Step("Check exchange rate value is displayed with text '{0}'.")
    public static void checkExchangeRateValueText(String exchangeRateValueText) {
        CurrenciesPage.getExchangeRateValue().shouldBe(Condition.visible)
                .shouldHave(Condition.text(exchangeRateValueText));
    }

    @Step("Check footer warning note is displayed with text '{0}'.")
    public static void checkFooterNoteText(String footerText) {
        CurrenciesPage.getFooterNote().shouldBe(Condition.visible).shouldHave(Condition.text(footerText));
    }

}
