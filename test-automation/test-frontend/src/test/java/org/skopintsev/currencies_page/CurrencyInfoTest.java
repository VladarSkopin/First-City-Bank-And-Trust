package org.skopintsev.currencies_page;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.util.OpenUrl;

public class CurrencyInfoTest extends BaseCurrencyTest {

    @Test
    @Tag("smoke")
    @Description("Test checks the display of currency information.")
    @Severity(SeverityLevel.NORMAL)
    public void checkCurrencyInfoTest() {
        OpenUrl.openCurrenciesPage();
    }

}
