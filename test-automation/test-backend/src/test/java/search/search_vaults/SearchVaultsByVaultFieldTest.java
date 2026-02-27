package search.search_vaults;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.vaults.SearchVaultsApiAssertions;
import org.skopintsev.assertions.db.CommonDbAssertions;
import org.skopintsev.database.factory.VaultDbFactory;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.model.vaults.Vault;
import org.skopintsev.transport.GetApiReqHelper;

import java.util.List;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchVaultsByVaultFieldTest extends BaseSearchVaultsTest {

    List<Vault> vaultsApiExpected;

    @BeforeEach
    public void beforeEach() {
        VaultDb vaultDb = VaultDbFactory.defaultVaultDbRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        int rowsInserted = VaultDbHelper.insertVault(vaultDb);
        CommonDbAssertions.checkRowsInserted(rowsInserted);

        vaultsApiExpected = List.of(
            Vault.builder()
                    .vaultCode(vaultDb.getVaultCode())
                    .clientCode(BASE_CLIENT_CODE)
                    .amount(vaultDb.getAmount())
                    .currencyCode(BASE_CURRENCY_CODE)
                    .isArchived(false)
                    .build()
        );
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for vaults by currency code.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchVaultsByCurrencyCodeTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByCurrencyAndValidate(BASE_CURRENCY_CODE, SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }

    @Test
    @Tag("smoke")
    @Description("Test uses API to search for vaults by client code.")
    @Severity(SeverityLevel.CRITICAL)
    public void searchVaultsByClientCodeTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByClientCodeAndValidate(BASE_CLIENT_CODE, SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }
}
