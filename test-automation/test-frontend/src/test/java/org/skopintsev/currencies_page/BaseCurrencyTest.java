package org.skopintsev.currencies_page;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.BaseTest;
import org.skopintsev.models.Currency;
import org.skopintsev.models.CurrencyFactory;
import org.skopintsev.transport.PostApiResponseHelper;

import java.util.List;

@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseCurrencyTest extends BaseTest {

    final List<Currency> currenciesList = CurrencyFactory.generateCurrenciesList();

    @BeforeEach
    public void openCurrenciesPage() {
        PostApiResponseHelper.postGetCurrencies(currenciesList);
    }

}
