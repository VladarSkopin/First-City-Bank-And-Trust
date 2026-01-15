package currency;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteCurrencyTest extends BaseCurrencyTest {

    // todo: code null
    @Test
    @Tag("regression")
    @Description(".")
    public void deleteCurrency() {

    }

    // todo: code null
    @Test
    @Tag("regression")
    @Description(".")
    public void deleteCurrencyNullCode() {

    }

    // todo: code empty
    @Test
    @Tag("regression")
    @Description(".")
    public void deleteCurrencyEmptyCode() {

    }

    // todo: code not found in db
    @Test
    @Tag("regression")
    @Description(".")
    public void deleteCurrencyNotFound() {

    }
}
