package org.skopintsev.currencies_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.CurrencyFactory;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseCurrencyTest extends BaseTest {

    final List<Currency> BASE_CURRENCIES_LIST = CurrencyFactory.generateCurrenciesList();

    @BeforeEach
    public void openCurrenciesPage() {
        PostApiResponseHelper.stubGetCurrencies(BASE_CURRENCIES_LIST);
        OpenUrl.openCurrenciesPage();
    }

}
