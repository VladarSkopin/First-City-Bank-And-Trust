package currency;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.skopintsev.database.currency.CurrencyDbHelper;

public class BaseCurrencyTest {
    @BeforeEach
    public void setUp() {
        CurrencyDbHelper.deleteAllTestCurrencies();
    }

    @AfterEach
    public void tearDown() {
        CurrencyDbHelper.deleteAllTestCurrencies();
    }
}
