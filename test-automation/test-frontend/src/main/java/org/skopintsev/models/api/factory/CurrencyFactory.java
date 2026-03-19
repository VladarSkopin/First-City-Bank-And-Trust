package org.skopintsev.models.api.factory;

import org.skopintsev.enums.MetalTypeEnum;
import org.skopintsev.models.api.Currency;
import org.skopintsev.util.GeneratorBuilder;

import java.util.List;

public class CurrencyFactory {

    public static List<Currency> generateCurrenciesList() {
        return List.of(
                Currency.builder()
                        .currencyCode(GeneratorBuilder.generateTestCode())
                        .currencyName(GeneratorBuilder.generateString(10))
                        .currencySymbol("*")
                        .metalType(MetalTypeEnum.GOLD.getText())
                        .build()
        );
    }

    public static List<Currency> generateMultipleCurrenciesList() {
        return List.of(
                Currency.builder()
                        .currencyCode(GeneratorBuilder.generateTestCode())
                        .currencyName(GeneratorBuilder.generateString(10))
                        .currencySymbol("*")
                        .metalType(MetalTypeEnum.GOLD.getText())
                        .build(),
                Currency.builder()
                        .currencyCode(GeneratorBuilder.generateTestCode())
                        .currencyName(GeneratorBuilder.generateString(10))
                        .currencySymbol("$")
                        .metalType(MetalTypeEnum.SILVER.getText())
                        .build(),
                Currency.builder()
                        .currencyCode(GeneratorBuilder.generateTestCode())
                        .currencyName(GeneratorBuilder.generateString(10))
                        .currencySymbol("@")
                        .metalType(MetalTypeEnum.COPPER.getText())
                        .build()
        );
    }

}
