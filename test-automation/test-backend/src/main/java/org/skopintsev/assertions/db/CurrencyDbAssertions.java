package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.helper.enums.MetalType;


public class CurrencyDbAssertions {

    @Step("Check currency name.")
    public static void checkCurrencyName(String actualCurrencyName, String expectedCurrencyName) {
        Assertions.assertThat(actualCurrencyName)
                .withFailMessage("Expected currency name = " + expectedCurrencyName + ", but actual = " + actualCurrencyName)
                .isEqualTo(expectedCurrencyName);
    }

    @Step("Check currency metal type.")
    public static void checkMetalType(String actualMetalType, String expectedMetalType) {
        Assertions.assertThat(actualMetalType)
                .withFailMessage("Expected metal type = " + expectedMetalType + ", but actual = " + actualMetalType)
                .isEqualTo(expectedMetalType);
    }

    @Step("Check currency default metal type.")
    public static void checkDefaultMetalType(String actualMetalType) {
        String defaultMetalType = MetalType.UNKNOWN.getText();
        Assertions.assertThat(actualMetalType)
                .withFailMessage("Expected default metal type = " + defaultMetalType + ", but actual = " + actualMetalType)
                .isEqualTo(defaultMetalType);
    }

    @Step("Check currency symbol.")
    public static void checkCurrencySymbol(String actualCurrencySymbol, String expectedCurrencySymbol) {
        Assertions.assertThat(actualCurrencySymbol)
                .withFailMessage("Expected currency symbol = " + expectedCurrencySymbol + ", but actual = " + actualCurrencySymbol)
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
