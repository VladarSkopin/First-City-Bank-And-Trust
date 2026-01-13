import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skopintsev.helper.enums.MetalType;
import org.skopintsev.model.Currency;


public class BasicCurrencyTest {

    Currency currencyBasic;

    @BeforeEach
    public void setUp() {
        currencyBasic = Currency.builder().currencyName("Test Currency").build();
    }

    @Test
    public void getCurrenciesTest() {
        Currency currency = Currency.builder().currencyCode("U-01").currencyName("Penny").build();
        System.out.println(currency.getCurrencyCode());
        System.out.println(currency.getCurrencyName());
        System.out.println(currency.getCurrencySymbol());
        System.out.println(currency.getMetalType());

        System.out.println("Currency basic = " + currencyBasic.getCurrencyName());
    }

    @Test
    public void getCurrenciesMetalTypeTest() {
        Currency currency = Currency.builder().currencyCode("U-02").currencyName("Frank").metalType(MetalType.SILVER.getText()).build();
        System.out.println(currency.getCurrencyCode());
        System.out.println(currency.getCurrencyName());
        System.out.println(currency.getCurrencySymbol());
        System.out.println(currency.getMetalType());

        System.out.println("Currency basic = " + currencyBasic.getCurrencyName());
    }
}
