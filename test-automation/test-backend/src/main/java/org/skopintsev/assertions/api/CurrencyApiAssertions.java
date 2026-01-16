package org.skopintsev.assertions.api;

import io.qameta.allure.Step;
import org.skopintsev.model.Currency;

import org.assertj.core.api.Assertions;

import java.util.List;

public class CurrencyApiAssertions {

    @Step("Check that currencies list is not null.")
    public static void checkNotNullCurrencies(List<Currency> currencies) {
        Assertions.assertThat(currencies)
                .withFailMessage("Expected currency list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }

}
