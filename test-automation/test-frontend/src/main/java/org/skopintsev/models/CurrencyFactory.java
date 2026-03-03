package org.skopintsev.models;

import java.util.List;

public class CurrencyFactory {

    public static List<Currency> generateCurrenciesList() {
        return List.of(
                Currency.builder().build(),
                Currency.builder().build(),
                Currency.builder().build()
        );
    }

}
