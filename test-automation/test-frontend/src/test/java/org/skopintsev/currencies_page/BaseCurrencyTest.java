package org.skopintsev.currencies_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.factory.CurrencyFactory;
import org.skopintsev.transport.PostApiResponseHelper;
import org.skopintsev.util.OpenUrl;

import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class BaseCurrencyTest extends BaseTest {

    List<Currency> BASE_CURRENCIES_LIST = CurrencyFactory.generateCurrenciesList();

    @BeforeEach
    public void openCurrenciesPage() {
        PostApiResponseHelper.stubGetCurrencies(BASE_CURRENCIES_LIST);
        OpenUrl.openCurrenciesPage();
    }

}
