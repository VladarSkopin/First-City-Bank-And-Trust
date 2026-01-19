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
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currency.CurrencyDb;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteCurrencyTest extends BaseCurrencyTest {

    @Test
    @Tag("regression")
    @Description("Test creates a new currency in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteCurrency() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        String currencyCode = GeneratorBuilder.generateTestCode();
        CurrencyDb newCurrencyDb = CurrencyDb.builder()
                .currencyCode(currencyCode)
                .currencyName(GeneratorBuilder.generateString(5))
                .build();
        int rowsInserted = CurrencyDbHelper.insertCurrency(newCurrencyDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteCurrencyAndValidate(currencyCode, SC_OK);

        newCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(currencyCode);
        CurrencyDbAssertions.checkCurrencyPresence(newCurrencyDb, false);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencyCode = {0}")
    @MethodSource("currencyCodeRequest")
    @Tag("regression")
    @Description("""
        Test uses API to delete a currency:
        1) with code = null,
        2) with code = empty string,
        3) a currency that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteCurrencyNegativeTest(String currencyCode) {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        DeleteApiReqHelper.deleteCurrencyAndValidate(currencyCode, SC_NOT_FOUND);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    private static Stream<Arguments> currencyCodeRequest() {
            return Stream.of(
                    Arguments.of((String) null),
                    Arguments.of(""),
                    Arguments.of(GeneratorBuilder.generateTestCode())
            );
    }
}
