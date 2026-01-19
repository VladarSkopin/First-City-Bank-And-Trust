package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.helper.enums.MetalType;


public class CurrencyDbAssertions {

    @Step("Check currency code.")
    public static void checkCurrencyCode(String actualCurrencyCode, String expectedCurrencyCode) {
        Assertions.assertThat(actualCurrencyCode)
                .withFailMessage("Expected currency code = '%s', but actual = '%s'", expectedCurrencyCode, actualCurrencyCode)
                .isEqualTo(expectedCurrencyCode);
    }

    @Step("Check currency name.")
    public static void checkCurrencyName(String actualCurrencyName, String expectedCurrencyName) {
        Assertions.assertThat(actualCurrencyName)
                .withFailMessage("Expected currency name = '%s', but actual = '%s'", expectedCurrencyName, actualCurrencyName)
                .isEqualTo(expectedCurrencyName);
    }

    @Step("Check currency metal type.")
    public static void checkMetalType(String actualMetalType, String expectedMetalType) {
        Assertions.assertThat(actualMetalType)
                .withFailMessage("Expected metal type = '%s', but actual = '%s'", expectedMetalType, actualMetalType)
                .isEqualTo(expectedMetalType);
    }

    @Step("Check currency default metal type.")
    public static void checkDefaultMetalType(String actualMetalType) {
        String defaultMetalType = MetalType.UNKNOWN.getText();
        Assertions.assertThat(actualMetalType)
                .withFailMessage("Expected default metal type = '%s', but actual = '%s'", defaultMetalType, actualMetalType)
                .isEqualTo(defaultMetalType);
    }

    @Step("Check currency symbol.")
    public static void checkCurrencySymbol(String actualCurrencySymbol, String expectedCurrencySymbol) {
        Assertions.assertThat(actualCurrencySymbol)
                .withFailMessage("Expected currency symbol = '%s', but actual = '%s'", expectedCurrencySymbol, actualCurrencySymbol)
                .isEqualTo(expectedCurrencySymbol);
    }

    @Step("Check currency presence in the Database.")
    public static void checkCurrencyPresence(CurrencyDb currencyDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(currencyDb)
                    .withFailMessage("Expected currency to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(currencyDb)
                    .withFailMessage("Expected currency to be NULL in the Database.")
                    .isNull();
        }
    }
}
