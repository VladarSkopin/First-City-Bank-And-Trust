package vault;

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
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateVaultTest extends BaseVaultTest {

    static final String VAULT_CODE = GeneratorBuilder.generateTestCode();

    @Test
    @Tag("regression")
    @Description("Test uses API to post a vault that is already present in the Database.")
    @Severity(SeverityLevel.CRITICAL)
    public void createVaultAlreadyExists() {
        Vault vault = VaultApiFactory.defaultVaultApiRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_OK);
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        PostApiReqHelper.saveVaultAndValidate(vault, SC_SERVER_ERROR);
        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] invalidVaultCode = {0}")
    @ValueSource(strings = {"", " "})
    @NullSource
    @Tag("regression")
    @Description(
            """
            Test uses API to post a vault:
            1) with vault code = null,
            2) with vault code = empty string,
            3) with vault code = whitespace.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createVaultWithInvalidCodeTest(String invalidVaultCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        Vault vault = VaultApiFactory.codeVaultApiRequest(
                invalidVaultCode,
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_SERVER_ERROR);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] clientCode = {0}")
    @MethodSource("vaultCodeRequest")
    @Tag("regression")
    @Description(
            """
            Test uses API to post a client:
            1) with client code that needs to be trimmed,
            2) with client code that should be modified to upper case,
            3) with client code in mixed case.
            """)
    @Severity(SeverityLevel.CRITICAL)
    public void createVaultCodeTrimUppercaseTest(String vaultCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();
        String vaultCodeTrimmedUppercase = vaultCode.trim().toUpperCase();

        Vault vault = VaultApiFactory.codeVaultApiRequest(
                vaultCode,
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_OK);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vaultCodeTrimmedUppercase);
        VaultDbAssertions.checkVaultPresence(vaultDb, true);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew - 1, vaultCountOld);
    }


    private static Stream<Arguments> vaultCodeRequest() {
        return Stream.of(
                Arguments.of(" " + VAULT_CODE + " "),
                Arguments.of(VAULT_CODE.toLowerCase()),
                Arguments.of(VAULT_CODE.charAt(0) + VAULT_CODE.substring(1).toLowerCase())
        );
    }
}
