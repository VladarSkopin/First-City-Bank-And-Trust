import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.skopintsev.assertions.CurrencyAssertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.enums.MetalType;
import org.skopintsev.model.Currency;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.transport.GetApiReqHelper.getCurrenciesAndValidate;
import static org.skopintsev.transport.PostApiReqHelper.saveCurrencyAndValidate;

// Lifecycle.PER_CLASS -> class variables = single instance created once, used in all test methods
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicCurrencyTest {

    @BeforeEach
    public void setUp() {
        CurrencyDbHelper.deleteAllTestCurrencies();
    }

    @AfterEach
    public void tearDown() {
        CurrencyDbHelper.deleteAllTestCurrencies();
    }

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Currency object into database and calls API to get all the currencies")
    public void createCurrenciesDbTest() {
        CurrencyDb newCurrency = CurrencyDb.builder()
                .currencyCode("TEST-01")
                .currencyName("Penny")
                .currencySymbol("₿")
                .build();
        int rowsInserted = CurrencyDbHelper.insertCurrency(newCurrency);
        assertEquals(1, rowsInserted, "Should insert 1 row");

        List<Currency> currencies = getCurrenciesAndValidate(SC_OK);
        CurrencyAssertions.checkNotNullCurrencies(currencies);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Currency object and calls API to get all the currencies")
    public void createCurrenciesApiTest() {
        Currency currency = Currency.builder()
                .currencyCode("TEST-02")
                .currencyName("Frank")
                .metalType(MetalType.SILVER.getText())
                .build();
        saveCurrencyAndValidate(currency, SC_OK);

        List<Currency> currencies = getCurrenciesAndValidate(SC_OK);
        CurrencyAssertions.checkNotNullCurrencies(currencies);
    }
}
