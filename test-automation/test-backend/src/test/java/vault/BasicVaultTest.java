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
import org.skopintsev.transport.GetApiReqHelper;

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
        // todo: replace with soft assertions ???
        VaultDbAssertions.checkVaultField("clientCode", vault.getClientCode(), vaultDb.getClientCode());
        VaultDbAssertions.checkVaultField("amount", vault.getAmount(), vaultDb.getAmount());
        VaultDbAssertions.checkVaultField("currencyCode", vault.getCurrencyCode(), vaultDb.getCurrencyCode());
        VaultDbAssertions.checkVaultField("isArchived", vault.getIsArchived(), vaultDb.getIsArchived());
    }

}
