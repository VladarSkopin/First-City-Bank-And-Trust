package org.skopintsev.currencies_page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.Collections;

public class CurrencyInfoTest extends BaseCurrencyTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of Currencies page information.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesPageInfoTest() {

        // todo: page title
        // todo: monetary system exchange rate
        // todo: footer note
        // todo: retry button not exist
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of single currency card information.")
    @Severity(SeverityLevel.NORMAL)
    public void currencyCardInfoTest() {

        // todo: currency code
        // todo: currency name
        // todo: currency metal type
        // todo: currency symbol
        // todo: retry button not exist
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of the Currencies page in case of empty response list.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesEmptyResponseTest() {
        PostApiResponseHelper.stubGetCurrencies(Collections.emptyList());
        //OpenUrl.openCurrenciesPage();
        Selenide.refresh();

        // todo: No Currencies Found
        // todo: No currency data is currently available
        // todo: retry button not exist
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of the Currencies page when no currencies were found.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesNotFoundTest() {
        PostApiResponseHelper.stubGetCurrenciesNotFound(Collections.emptyList());
        //OpenUrl.openCurrenciesPage();
        Selenide.refresh();

        // todo: Failed to Load Currencies
        // todo: HTTP error! status: 404
        // todo: retry button visible enabled
    }

    @Test
    @Tag("smoke")
    @Description("Test checks the display of the Currencies page in case of server error response.")
    @Severity(SeverityLevel.NORMAL)
    public void currenciesServerErrorTest() {
        PostApiResponseHelper.stubGetCurrenciesServerError(Collections.emptyList());
        //OpenUrl.openCurrenciesPage();
        Selenide.refresh();

        // todo: Failed to Load Currencies
        // todo: Failed to fetch
        // todo: retry button visible enabled
    }

}
