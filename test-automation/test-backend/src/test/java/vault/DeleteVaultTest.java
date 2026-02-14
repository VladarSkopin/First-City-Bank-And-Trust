package vault;

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
import org.skopintsev.assertions.db.vaults.VaultDbAssertions;
import org.skopintsev.database.factory.VaultDbFactory;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.helper.GeneratorBuilder;
import org.skopintsev.transport.DeleteApiReqHelper;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteVaultTest extends BaseVaultTest {

    @Test
    @Tag("regression")
    @Description(
        """
        Test creates a new vault in the Database with:
        - archived status = true;
        - amount = 0.
        And uses API to delete it.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVaultTest() {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        VaultDb vaultDb = VaultDbFactory.amountVaultDbRequest(
                BASE_CLIENT_CODE,
                BigInteger.valueOf(0),
                BASE_CURRENCY_CODE
        );
        vaultDb.setIsArchived(true);
        int rowsInserted = VaultDbHelper.insertVault(vaultDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        String vaultCode = vaultDb.getVaultCode();
        DeleteApiReqHelper.deleteVaultAndValidate(vaultCode, SC_OK);

        vaultDb = VaultDbHelper.selectVaultByCode(vaultCode);
        VaultDbAssertions.checkVaultPresence(vaultDb, false);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @ParameterizedTest(name = "[{index}] vaultCode = {0}")
    @MethodSource("vaultCodeRequest")
    @Tag("regression")
    @Description(
        """
        Test uses API to delete a vault:
        1) with code = null,
        2) with code = empty string,
        3) with code = whitespace,
        4) a vault that is absent in the Database.
        """)
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVaultNegativeTest(String vaultCode) {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        DeleteApiReqHelper.deleteVaultAndValidate(vaultCode, SC_NOT_FOUND);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew, vaultCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to delete a vault that is not archived yet (isArchived = false).")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVaultNotArchived() {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        VaultDb vaultDb = VaultDbFactory.amountVaultDbRequest(
                BASE_CLIENT_CODE,
                BigInteger.valueOf(0),
                BASE_CURRENCY_CODE
        );
        int rowsInserted = VaultDbHelper.insertVault(vaultDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteVaultAndValidate(vaultDb.getVaultCode(), SC_SERVER_ERROR);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew - 1, vaultCountOld);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to delete a vault with amount > 0.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteVaultAmountNotZero() {
        int vaultCountOld = VaultDbHelper.getVaultsCount();

        VaultDb vaultDb = VaultDbFactory.defaultVaultDbRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        vaultDb.setIsArchived(true);
        int rowsInserted = VaultDbHelper.insertVault(vaultDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        DeleteApiReqHelper.deleteVaultAndValidate(vaultDb.getVaultCode(), SC_SERVER_ERROR);

        int vaultCountNew = VaultDbHelper.getVaultsCount();
        CommonDbAssertions.checkCounts(vaultCountNew - 1, vaultCountOld);
    }


    private static Stream<Arguments> vaultCodeRequest() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of(GeneratorBuilder.generateTestCode())
        );
    }

}
