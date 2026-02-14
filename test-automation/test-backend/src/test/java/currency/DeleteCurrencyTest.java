package currency;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.CurrencyDbAssertions;
import org.skopintsev.database.currencies.CurrencyDb;
import org.skopintsev.database.currencies.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteCurrencyTest extends BaseCurrencyTest {

    final String CURRENCY_CODE = GeneratorBuilder.generateTestCode();

    @Test
    @Tag("regression")
    @Description("Test creates a new currency in the Database and uses API to delete it.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteCurrencyTest() {
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        CurrencyDb newCurrencyDb = CurrencyDb.builder()
                .currencyCode(CURRENCY_CODE)
                .currencyName(GeneratorBuilder.generateString(5))
                .build();
        int rowsInserted = CurrencyDbHelper.insertCurrency(newCurrencyDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteCurrencyAndValidate(CURRENCY_CODE, SC_OK);

        newCurrencyDb = CurrencyDbHelper.selectCurrencyByCode(CURRENCY_CODE);
        CurrencyDbAssertions.checkCurrencyPresence(newCurrencyDb, false);

        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();
        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencyCode = {0}")
    @MethodSource("currencyCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a currency:
        1) with code = null,
        2) with code = empty string,
        3) with code = whitespace,
        4) a currency that is absent in the Database.
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
                    Arguments.of(" "),
                    Arguments.of(GeneratorBuilder.generateTestCode())
            );
    }
}
