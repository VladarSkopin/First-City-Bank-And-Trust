package org.skopintsev.assertions;

import io.qameta.allure.Step;
import org.skopintsev.model.Currency;

import org.assertj.core.api.Assertions;

import java.util.List;

public class CurrencyAssertions {

    @Step("Check that currencies list is not null")
    public static void checkNotNullCurrencies(List<Currency> currencies) {
        Assertions.assertThat(currencies).isNotNull().isNotEmpty();
    }
}
