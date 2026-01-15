package currency;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteCurrencyTest extends BaseCurrencyTest {

    @Test
    @Tag("regression")
    @Description("Test creates a new currency in the Database and uses API to delete it.")
    public void deleteCurrency() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        String currencyCode = GeneratorBuilder.generateTestCode();
        CurrencyDb newCurrencyDb = CurrencyDb.builder()
                .currencyCode(currencyCode)
                .currencyName(GeneratorBuilder.generateString(5))
                .build();
        int rowsInserted = CurrencyDbHelper.insertCurrency(newCurrencyDb);
        assertEquals(1, rowsInserted, "Should insert 1 row");

        DeleteApiReqHelper.deleteCurrencyAndValidate(currencyCode, SC_OK);

        newCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(currencyCode);
        CurrencyDbAssertions.checkCurrencyPresence(newCurrencyDb, false);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to delete a currency with code = null.")
    public void deleteCurrencyNullCode() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        DeleteApiReqHelper.deleteCurrencyAndValidate(null, SC_NOT_FOUND);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to delete a currency with code = empty string.")
    public void deleteCurrencyEmptyCode() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        DeleteApiReqHelper.deleteCurrencyAndValidate("", SC_NOT_FOUND);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to delete a currency that is absent in the Database.")
    public void deleteCurrencyAbsent() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        DeleteApiReqHelper.deleteCurrencyAndValidate(GeneratorBuilder.generateTestCode(), SC_NOT_FOUND);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }
}
