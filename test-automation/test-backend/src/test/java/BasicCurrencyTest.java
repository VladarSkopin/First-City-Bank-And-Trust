import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.skopintsev.helper.enums.MetalType;
import org.skopintsev.model.Currency;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.transport.GetApiReqHelper.getCurrenciesAndValidate;
import static org.skopintsev.transport.PostApiReqHelper.saveCurrencyAndValidate;


public class BasicCurrencyTest {

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Currency object into database and calls API to get all the currencies")
    public void createCurrenciesDbTest() {
        // todo: replace with a Db insert
        Currency currency = Currency.builder()
                .currencyCode("U-01")
                .currencyName("Penny")
                .build();
        saveCurrencyAndValidate(currency, SC_OK);

        List<Currency> currencies = getCurrenciesAndValidate(SC_OK);

        // todo: move to custom Assertions + add more checks
        assertNotNull(currencies);
        // Verify the new currency is in the list
        boolean found = currencies.stream()
                .anyMatch(c -> c.getCurrencyCode().equals("U-01"));
        assertTrue(found, "New currency should be in the list");

        // todo: delete from Db + afterEach / afterAll ?
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Currency object and calls API to get all the currencies")
    public void createCurrenciesApiTest() {
        Currency currency = Currency.builder()
                .currencyCode("U-02")
                .currencyName("Frank")
                .metalType(MetalType.SILVER.getText())
                .build();
        saveCurrencyAndValidate(currency, SC_OK);

        List<Currency> currencies = getCurrenciesAndValidate(SC_OK);

        // todo: move to custom Assertions + add more checks
        assertNotNull(currencies);
        // Find our currency
        Currency savedCurrency = currencies.stream()
                .filter(c -> c.getCurrencyCode().equals("U-02"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Currency not found"));

        // Verify all properties
        assertEquals("U-02", savedCurrency.getCurrencyCode());
        assertEquals("Frank", savedCurrency.getCurrencyName());
        assertEquals(MetalType.SILVER.getText(), savedCurrency.getMetalType());
        assertEquals("*", savedCurrency.getCurrencySymbol()); // Default value

        // todo: delete from Db + afterEach / afterAll ?
    }
}
