package org.skopintsev.database.currency;

import io.qameta.allure.Step;
import org.skopintsev.database.DatabaseHelper;

import java.util.List;

public class CurrencyDbHelper {

    @Step("Select all currencies from database")
    public static List<CurrencyDb> selectAllCurrencies() {
        String query = "SELECT currency_code, currency_name, currency_symbol, metal_type FROM currencies ORDER BY currency_code";
        return DatabaseHelper.executeQuery(query);
    }

    @Step("Select currency by code: {currencyCode}")
    public static CurrencyDb selectCurrencyByCode(String currencyCode) {
        String query = "SELECT currency_code, currency_name, currency_symbol, metal_type FROM currencies WHERE currency_code = ?";
        List<CurrencyDb> results = DatabaseHelper.executeQuery(query, currencyCode);
        return results.isEmpty() ? null : results.get(0);
    }

    @Step("Insert new currency: {currency}")
    public static int insertCurrency(CurrencyDb currency) {
        String query = """
            INSERT INTO currencies (currency_code, currency_name, currency_symbol, metal_type)
            VALUES (?, ?, ?, ?)
            """;
        return DatabaseHelper.executeUpdate(
                query,
                currency.getCurrencyCode(),
                currency.getCurrencyName(),
                currency.getCurrencySymbol(),
                currency.getMetalType()
        );
    }

    @Step("Delete currency by code: {currencyCode}")
    public static int deleteCurrency(String currencyCode) {
        String query = "DELETE FROM currencies WHERE currency_code = ?";
        return DatabaseHelper.executeUpdate(query, currencyCode);
    }

    @Step("Delete all currencies")
    public static int deleteAllTestCurrencies() {
        String query = "DELETE FROM currencies WHERE currency_code NOT LIKE 'BBC-%'";
        return DatabaseHelper.executeUpdate(query);
    }




}
