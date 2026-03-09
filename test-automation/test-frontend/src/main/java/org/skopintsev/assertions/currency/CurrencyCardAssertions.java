package org.skopintsev.assertions.currency;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.skopintsev.models.gui.currencies.CurrencyCard;

public class CurrencyCardAssertions {

    @Step("Check currency code is displayed with text '{0}'.")
    public static void checkCurrencyCode(String currencyCode) {
        CurrencyCard.getCurrencyCode().shouldBe(Condition.visible).shouldHave(Condition.text(currencyCode));
    }

    @Step("Check currency name is displayed with text '{0}'.")
    public static void checkCurrencyName(String currencyName) {
        CurrencyCard.getCurrencyName().shouldBe(Condition.visible).shouldHave(Condition.text(currencyName));
    }

    @Step("Check metal type label is displayed with text '{0}'.")
    public static void checkMetalTypeLabel(String metalTypeLabel) {
        CurrencyCard.getCurrencyMetalTypeLabel().shouldBe(Condition.visible).shouldHave(Condition.text(metalTypeLabel));
    }

    @Step("Check metal type value is displayed with text '{0}'.")
    public static void checkMetalTypeValue(String metalTypeValue) {
        CurrencyCard.getCurrencyMetalTypeValue().shouldBe(Condition.visible).shouldHave(Condition.text(metalTypeValue));
    }

    @Step("Check currency symbol is displayed with text '{0}'.")
    public static void checkCurrencySymbol(String currencySymbol) {
        CurrencyCard.getCurrencySymbol().shouldBe(Condition.visible).shouldHave(Condition.text(currencySymbol));
    }

}
