package currency;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Currency;
import org.skopintsev.transport.PostApiReqHelper;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateCurrencyTest extends BaseCurrencyTest {

    String currencyCode = GeneratorBuilder.generateTestCode();
    String currencyName = GeneratorBuilder.generateString(5);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyAlreadyExists() {
        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);
        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencyCode = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a Currency:
        1) with currency code = null,
        2) with currency code = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidCode(String currencyCode) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency with currency name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyNameAlreadyExists() {
        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);

        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApiSameName = Currency.builder()
                .currencyCode(GeneratorBuilder.generateTestCode())
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApiSameName, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencyName = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a Currency:
        1) with currency name = null,
        2) with currency name = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidName(String currencyName) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencySymbol = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a Currency:
        1) with currency symbol = null,
        2) with currency symbol = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidSymbol(String currencySymbol) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .currencySymbol(currencySymbol)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] metalType = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description("""
        Test uses API to post a Currency:
        1) with currency metal type = null,
        2) with currency metal type = empty string.
        In both cases expected metal type should be = 'UNKNOWN'.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithDefaultMetalType(String metalType) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .metalType(metalType)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount() - 1;
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);

        CurrencyDb newAddedCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(currencyCode);
        CurrencyDbAssertions.checkDefaultMetalType(newAddedCurrencyDb.getMetalType());
    }
}
