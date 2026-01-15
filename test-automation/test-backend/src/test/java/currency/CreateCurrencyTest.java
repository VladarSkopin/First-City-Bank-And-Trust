package currency;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.currency.CurrencyDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.helper.enums.MetalType;
import org.skopintsev.model.Currency;
import org.skopintsev.transport.PostApiReqHelper;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateCurrencyTest extends BaseCurrencyTest {

    String currencyCode = GeneratorBuilder.generateTestCode();
    String currencyName = GeneratorBuilder.generateString(5);
    String metalType = MetalType.COPPER.getText();

    @Test
    @Tag("regression")
    @Description("Test uses API to post a Currency that is already present in the Database.")
    public void createCurrencyAlreadyExists() {
        Currency newCurrencyApi = Currency.builder()
                .currencyCode(currencyCode)
                .currencyName(currencyName)
                .metalType(metalType)
                .build();
        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_OK);
        int currenciesCountOld = CurrencyDbHelper.getCurrenciesCount();

        PostApiReqHelper.saveCurrencyAndValidate(newCurrencyApi, SC_SERVER_ERROR);
        int currenciesCountNew = CurrencyDbHelper.getCurrenciesCount();

        CommonDbAssertions.checkCounts(currenciesCountNew, currenciesCountOld);
    }


    // todo: code null
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyNullCode() {

    }

    // todo: code empty
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyEmptyCode() {

    }

    // todo: name already exists
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyNameAlreadyExists() {

    }

    // todo: name null
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyNullName() {

    }

    // todo: name empty
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyEmptyName() {

    }

    // todo: symbol null
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyNullSymbol() {

    }

    // todo: symbol empty
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyEmptySymbol() {

    }

    // todo: metal type null
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyNullMetalType() {

    }

    // todo: metal type empty
    @Test
    @Tag("regression")
    @Description(".")
    public void createCurrencyEmptyMetalType() {

    }
}
