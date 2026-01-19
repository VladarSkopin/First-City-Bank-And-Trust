package currency;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import org.skopintsev.assertions.api.CurrencyApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.MetalType;
import org.skopintsev.model.Currency;
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicCurrencyTest extends BaseCurrencyTest {

    String currencyCode = GeneratorBuilder.generateTestCode();
    String currencyName = GeneratorBuilder.generateString(5);

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Currency object into the Database and checks API for the new added currency.")
    @Severity(SeverityLevel.BLOCKER)
    public void createCurrencyDbTest() {
        String currencySymbol = "₿";
        CurrencyDb newCurrencyDb = CurrencyDb.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .currencySymbol(currencySymbol)
                .build();
        int rowsInserted = CurrencyDbHelper.insertCurrency(newCurrencyDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<Currency> currencies = GetApiReqHelper.getCurrenciesAndValidate(SC_OK);
        CurrencyApiAssertions.checkNotNullCurrencies(currencies);

        Currency newAddedCurrencyApi = currencies
                .stream()
                .filter(c -> c.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElse(null);
        CurrencyDbAssertions.checkCurrencyName(newAddedCurrencyApi.getCurrencyName(), currencyName);
        CurrencyDbAssertions.checkDefaultMetalType(newAddedCurrencyApi.getMetalType());
        CurrencyDbAssertions.checkCurrencySymbol(newAddedCurrencyApi.getCurrencySymbol(), currencySymbol);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Currency object and checks Database for the new added currency.")
    @Severity(SeverityLevel.BLOCKER)
    public void createCurrencyApiTest() {
        String metalType = MetalType.SILVER.getText();
        Currency newAddedCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .metalType(metalType)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newAddedCurrencyApi, SC_OK);

        List<Currency> currencies = GetApiReqHelper.getCurrenciesAndValidate(SC_OK);
        CurrencyApiAssertions.checkNotNullCurrencies(currencies);

        CurrencyDb newAddedCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(currencyCode);
        CurrencyDbAssertions.checkCurrencyPresence(newAddedCurrencyDb, true);
        CurrencyDbAssertions.checkCurrencyName(newAddedCurrencyDb.getCurrencyName(), currencyName);
        CurrencyDbAssertions.checkMetalType(newAddedCurrencyDb.getMetalType(), metalType);
        CurrencyDbAssertions.checkCurrencySymbol(newAddedCurrencyDb.getCurrencySymbol(), "*");
    }
}
