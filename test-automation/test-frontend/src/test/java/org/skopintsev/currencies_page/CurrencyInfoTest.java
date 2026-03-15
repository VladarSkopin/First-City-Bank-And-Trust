package org.skopintsev.currencies_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.assertions.common.elements.ButtonElementAssertions;
import org.skopintsev.assertions.currency.CurrenciesPageAssertions;
import org.skopintsev.assertions.currency.CurrencyCardAssertions;
import org.skopintsev.models.api.Currency;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class CurrencyInfoTest extends BaseCurrencyTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Currencies page information.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesPageInfoTest() {
        CurrenciesPageAssertions.checkPageTitleText("City Currencies");
        CurrenciesPageAssertions.checkExchangeRateTitleText("MONETARY SYSTEM");
        CurrenciesPageAssertions.checkExchangeRateLabelText("OFFICIAL EXCHANGE:");
        CurrenciesPageAssertions.checkExchangeRateValueText("1 GOLD = 20 SILVER = 240 COPPER");
        CurrenciesPageAssertions.checkFooterNoteText("COUNTERFEITING PUNISHABLE BY CRAGSLEFT IMPRISONMENT");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single currency card information.")
    @Severity(SeverityLevel.NORMAL)
    public void currencyCardInfoTest() {
        Currency currency = BASE_CURRENCIES_LIST.get(0);

        CurrencyCardAssertions.checkCurrencyCodeText(currency.getCurrencyCode());
        CurrencyCardAssertions.checkCurrencyName(currency.getCurrencyName());
        CurrencyCardAssertions.checkMetalTypeLabelText("METAL TYPE:");
        CurrencyCardAssertions.checkMetalTypeValueText(currency.getMetalType());
        CurrencyCardAssertions.checkCurrencySymbol(currency.getCurrencySymbol());
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Currencies page in case of empty response list.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesEmptyResponseTest() {
        PostApiResponseHelper.stubGetCurrencies(Collections.emptyList());
        Selenide.refresh();

        CurrenciesPageAssertions.checkPageTitleText("No Currencies Found");
        ButtonElementAssertions.checkRetryBtnExists(false);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Currencies page when no currencies were found.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesNotFoundTest() {
        PostApiResponseHelper.stubGetCurrenciesNotFound(Collections.emptyList());
        Selenide.refresh();

        CurrenciesPageAssertions.checkPageTitleText("Failed to Load Currencies");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

    @Test
    @Tag("regression")
    @Description("Test checks the display of the Currencies page in case of server error response.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesServerErrorTest() {
        PostApiResponseHelper.stubGetCurrenciesServerError(Collections.emptyList());
        Selenide.refresh();

        CurrenciesPageAssertions.checkPageTitleText("Failed to Load Currencies");
        ButtonElementAssertions.checkRetryBtnExists(true);
        ButtonElementAssertions.checkRetryBtnIsVisible();
        ButtonElementAssertions.checkRetryBtnEnabled(true);
    }

}
