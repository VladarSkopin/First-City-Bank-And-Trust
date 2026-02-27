package search.search_vaults;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
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
public class SearchVaultByClientFieldTest extends BaseSearchVaultsTest {

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

    public void searchVaultsByClientNameTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByClientNameAndValidate("", SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }

    public void searchVaultsByClientRankTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByClientRankAndValidate("", SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }

    public void searchVaultsByClientTypeTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByClientTypeAndValidate("", SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }

    public void searchVaultsByClientSectorTest() {
        List<Vault> vaultsApiFound = GetApiReqHelper.searchVaultsByClientSectorAndValidate("", SC_OK);
        SearchVaultsApiAssertions.checkSearchVaultsResponseMatchesExpected(vaultsApiFound, vaultsApiExpected);
    }
}
