package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.vaults.VaultApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.assertions.db.vaults.VaultDbAssertions;
import org.skopintsev.database.factory.VaultDbFactory;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.model.Vault;
import org.skopintsev.model.factory.VaultApiFactory;
import org.skopintsev.transport.GetApiReqHelper;
import org.skopintsev.transport.PostApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BasicVaultTest extends BaseVaultTest {

    @Test
    @Tag("smoke")
    @Description("Test inserts a new Vault object into the Database and checks API for the new added vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void createVaultDbTest() {
        VaultDb vaultDb = VaultDbFactory.defaultVaultDbRequest(
            BASE_CLIENT_CODE,
            BASE_CURRENCY_CODE
        );
        int rowsInserted = VaultDbHelper.insertVault(vaultDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        List<Vault> vaults = GetApiReqHelper.getVaultsAndValidate(SC_OK);
        VaultApiAssertions.checkNotNullVaults(vaults);

        Vault vault = vaults
                .stream()
                .filter(v -> v.getVaultCode().equals(vaultDb.getVaultCode()))
                .findFirst()
                .orElse(null);
        VaultDbAssertions.checkVaultMatchesDb(vault, vaultDb);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to post a new Vault object and checks Database for the new added vault.")
    @Severity(SeverityLevel.BLOCKER)
    public void createVaultApiTest() {
        Vault vault = VaultApiFactory.defaultVaultApiRequest(
            BASE_CLIENT_CODE,
            BASE_CURRENCY_CODE
        );
        PostApiReqHelper.saveVaultAndValidate(vault, SC_OK);

        List<Vault> vaults = GetApiReqHelper.getVaultsAndValidate(SC_OK);
        VaultApiAssertions.checkNotNullVaults(vaults);

        VaultDb vaultDb = VaultDbHelper.selectVaultByCode(vault.getVaultCode());
        VaultDbAssertions.checkVaultPresence(vaultDb, true);
        VaultDbAssertions.checkVaultMatchesDb(vault, vaultDb);
    }
}
