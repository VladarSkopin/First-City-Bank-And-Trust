package currency;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currencies.CurrencyDb;
import org.skopintsev.database.currencies.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Currency;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateCurrencyTest extends BaseCurrencyTest {

    private final String CURRENCY_CODE = GeneratorBuilder.generateTestCode();
    private final String CURRENCY_NAME = GeneratorBuilder.generateString(5);

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyAlreadyExistsTest() {
        Currency newCurrencyApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(CURRENCY_NAME)
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
    @Description(
        """
        Test uses API to post a Currency:
        1) with currency code = null,
        2) with currency code = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidCodeTest(String currencyCode) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(CURRENCY_NAME)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);

        CurrencyDb currencyDb = CurrencyDbHelper.selectCurrencyByCode(currencyCode);
        CurrencyDbAssertions.checkCurrencyPresence(currencyDb, false);
    }

    @ParameterizedTest(name = "[{index}] currencyCode = {0}")
    @MethodSource("currencyCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a Currency:
        1) with currency code that needs to be trimmed,
        2) with currency code that should be modified to upper case.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyCodeTrimUppercaseTest(String currencyCode) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        String currencyCodeTrimmedUppercase = currencyCode.trim().toUpperCase();

        Currency currencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(CURRENCY_NAME)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(currencyApi, SC_OK);

        CurrencyDb districtDb = CurrencyDbHelper.selectCurrencyByCode(currencyCodeTrimmedUppercase);
        CurrencyDbAssertions.checkCurrencyPresence(districtDb, true);
        CurrencyDbAssertions.checkCurrencyCode(districtDb.getCurrencyCode(), currencyCodeTrimmedUppercase);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew - 1, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency with currency name already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyNameAlreadyExistsTest() {
        Currency newCurrencyApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(CURRENCY_NAME)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);

        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApiSameName = Currency.builder()
                .currencyCode(GeneratorBuilder.generateTestCode())
                .currencyName(CURRENCY_NAME)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApiSameName, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency with currency name that needs to be trimmed.")
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyNameTrimTest() {
        String currencyNameToTrim = " " + GeneratorBuilder.generateString(10) + " ";
        String currencyNameTrimmed = currencyNameToTrim.trim();

        Currency districtApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(currencyNameToTrim)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(districtApi, SC_OK);

        CurrencyDb currencyDb = CurrencyDbHelper.selectCurrencyByCode(CURRENCY_CODE);
        CurrencyDbAssertions.checkCurrencyPresence(currencyDb, true);
        CurrencyDbAssertions.checkCurrencyName(currencyDb.getCurrencyName(), currencyNameTrimmed);
    }

    @ParameterizedTest(name = "[{index}] currencyName = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a Currency:
        1) with currency name = null,
        2) with currency name = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidNameTest(String currencyName) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(currencyName)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);

        CurrencyDb currencyDb = CurrencyDbHelper.selectCurrencyByCode(CURRENCY_CODE);
        CurrencyDbAssertions.checkCurrencyPresence(currencyDb, false);
    }

    @ParameterizedTest(name = "[{index}] currencySymbol = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a Currency:
        1) with currency symbol = null,
        2) with currency symbol = empty string.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithInvalidSymbolTest(String currencySymbol) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(CURRENCY_NAME)
                .currencySymbol(currencySymbol)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);

        CurrencyDb currencyDb = CurrencyDbHelper.selectCurrencyByCode(CURRENCY_CODE);
        CurrencyDbAssertions.checkCurrencyPresence(currencyDb, false);
    }

    @ParameterizedTest(name = "[{index}] metalType = {0}")
    @ValueSource(strings = {""})
    @NullSource
    @Tag("regression")
    @Description(
        """
        Test uses API to post a Currency:
        1) with currency metal type = null,
        2) with currency metal type = empty string.
        In both cases expected metal type should be = 'UNKNOWN'.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void createCurrencyWithDefaultMetalTypeTest(String metalType) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        Currency newCurrencyApi = Currency.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(CURRENCY_NAME)
                .metalType(metalType)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew - 1, currenciesCountOld);

        CurrencyDb newAddedCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(CURRENCY_CODE);
        CurrencyDbAssertions.checkCurrencyPresence(newAddedCurrencyDb, true);
        CurrencyDbAssertions.checkDefaultMetalType(newAddedCurrencyDb.getMetalType());
    }

    private static Stream<Arguments> currencyCodeRequest() {
        return Stream.of(
                Arguments.of(" " + GeneratorBuilder.generateTestCode() + " "),
                Arguments.of(GeneratorBuilder.generateTestCode().toLowerCase())
        );
    }
}
