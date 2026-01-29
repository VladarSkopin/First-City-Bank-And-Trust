package org.skopintsev.database.currencies;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.skopintsev.database.DatabaseHelper;

import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CurrencyDbHelper {

    private static CurrencyDb mapRow(ResultSet rs) {
        try {
            return CurrencyDb.builder()
                    .currencyCode(rs.getString("currency_code"))
                    .currencyName(rs.getString("currency_name"))
                    .currencySymbol(rs.getString("currency_symbol"))
                    .metalType(rs.getString("metal_type"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping CurrencyDb from ResultSet", e);
        }
    }

    @SneakyThrows
    @Step("Select all currencies from database.")
    public static List<CurrencyDb> selectAllCurrencies() {
        String query = "SELECT currency_code, currency_name, currency_symbol, metal_type FROM currencies ORDER BY currency_code";
        return DatabaseHelper.executeQuery(query, CurrencyDbHelper::mapRow);
    }

    @SneakyThrows
    @Step("Select currency by code: {currencyCode}")
    public static CurrencyDb selectCurrencyByCode(String currencyCode) {
        String query = "SELECT currency_code, currency_name, currency_symbol, metal_type FROM currencies WHERE currency_code = ?";
        return DatabaseHelper.queryForObject(query, CurrencyDbHelper::mapRow, currencyCode);
    }

    @SneakyThrows
    @Step("Select currencies by metal type: {metalType}")
    public static List<CurrencyDb> selectCurrenciesByMetalType(String metalType) {
        String query = "SELECT currency_code, currency_name, currency_symbol, metal_type FROM currencies WHERE metal_type = ?";
        return DatabaseHelper.executeQuery(query, CurrencyDbHelper::mapRow, metalType);
    }

    @Step("Insert new currency: {currency}")
    public static int insertCurrency(CurrencyDb currencyDb) {
        String query = """
            INSERT INTO currencies (currency_code, currency_name, currency_symbol, metal_type)
            VALUES (?, ?, ?, ?)
            """;
        return DatabaseHelper.executeUpdate(
                query,
                currencyDb.getCurrencyCode(),
                currencyDb.getCurrencyName(),
                currencyDb.getCurrencySymbol(),
                currencyDb.getMetalType()
        );
    }

    @Step("Delete currency by code: {currencyCode}")
    public static int deleteCurrency(String currencyCode) {
        String query = "DELETE FROM currencies WHERE currency_code = ?";
        return DatabaseHelper.executeUpdate(query, currencyCode);
    }

    @Step("Delete all test currencies.")
    public static void deleteAllTestCurrencies() {
        String query = "DELETE FROM currencies WHERE currency_code LIKE 'TEST-%'";
        DatabaseHelper.executeUpdate(query);
    }

    @Step("Get currencies count.")
    public static int getCurrenciesCount() {
        String query = "SELECT COUNT(*) FROM currencies";
        Integer count = DatabaseHelper.queryForObject(query,
                rs -> {
                    try {
                        return rs.getInt(1);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error getting count", e);
                    }
                }
        );
        return count != null ? count : 0;
    }

}
