package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.vaults.VaultDbAssertions;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.model.Vault;
import org.skopintsev.model.factory.VaultApiFactory;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.SC_OK;
import static org.skopintsev.constants.Constants.SC_SERVER_ERROR;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateVaultAdditionalFieldsTest extends BaseVaultTest {

    @ParameterizedTest(name = "[{index}] clientCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a vault:
        1) with client code = null,
        2) with client code = empty string,
        3) with client code = whitespace,
        4) with client code absent on the database.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void createVaultInvalidClientCodeTest(String clientCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        Vault vault = VaultApiFactory.defaultVaultApiRequest(
                clientCode,
                BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_SERVER_ERROR);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vault.getVaultCode());
        VaultDbAssertions.checkVaultPresence(vaultDb, false);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] currencyCode = {0}")
    @MethodSource("testCodeInvalidRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a vault:
        1) with currency code = null,
        2) with currency code = empty string,
        3) with currency code = whitespace,
        4) with currency code absent on the database.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void createVaultInvalidCurrencyCodeTest(String currencyCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        Vault vault = VaultApiFactory.defaultVaultApiRequest(
                BASE_CLIENT_CODE,
                currencyCode
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_SERVER_ERROR);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vault.getVaultCode());
        VaultDbAssertions.checkVaultPresence(vaultDb, false);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] String clientCode = {0}")
    @MethodSource("clientCodeValidRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a vault:
        1) with client code that needs to be trimmed,
        2) with client code in lowercase,
        3) with client code in mixed case.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void createVaultValidClientCodeTest(String clientCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        Vault vault = VaultApiFactory.defaultVaultApiRequest(
                clientCode,
                BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_OK);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vault.getVaultCode());
        VaultDbAssertions.checkVaultPresence(vaultDb, true);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew - 1, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] String currencyCode = {0}")
    @MethodSource("currencyCodeValidRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to post a vault:
        1) with currency code that needs to be trimmed,
        2) with currency code in lowercase,
        3) with currency code in mixed case.
        """)
    @Severity(SeverityLevel.NORMAL)
    public void createVaultValidCurrencyCodeTest(String currencyCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        Vault vault = VaultApiFactory.defaultVaultApiRequest(
                BASE_CLIENT_CODE,
                currencyCode
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_OK);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vault.getVaultCode());
        VaultDbAssertions.checkVaultPresence(vaultDb, true);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew - 1, vaultCountOld);
    }


    // generates invalid test codes (null, "", " ", absent in the database)
    private static Stream<Arguments> testCodeInvalidRequest() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }

    // generates valid test codes (untrimmed, lowercase, mixed case)
    private static Stream<Arguments> generateCodeVariations(String testCode) {
        return Stream.of(
                Arguments.of(" " + testCode + " "),
                Arguments.of(testCode.toLowerCase()),
                Arguments.of(testCode.charAt(0) + testCode.substring(1).toLowerCase())
        );
    }

    private static Stream<Arguments> clientCodeValidRequest() {
        return generateCodeVariations(BASE_CLIENT_CODE);
    }

    private static Stream<Arguments> currencyCodeValidRequest() {
        return generateCodeVariations(BASE_CURRENCY_CODE);
    }
}
