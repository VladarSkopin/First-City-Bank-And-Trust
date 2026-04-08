package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.model.vaults.vaultstats.VaultStatsRequest;
import org.skopintsev.transport.api.VaultsApiClient;

import static org.skopintsev.constants.Constants.SC_OK;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VaultStatsTest extends BaseVaultTest {

    @Test
    @Tag("regression")
    @Description("Test uses API get vault statistics when there are more vaults than requested limit.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsMoreThanLimit() {
        // todo: vaults more than limit
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder().build();
        VaultsApiClient.getVaultStats(vaultStatsRequest, SC_OK);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API get vault statistics when there are less vaults than requested limit.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsLessThanLimit() {
        // todo: vaults less than limit
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder().build();
        VaultsApiClient.getVaultStats(vaultStatsRequest, SC_OK);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API get vault statistics by sector.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsBySector() {
        // todo: limit = 1, by sector
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder().build();
        VaultsApiClient.getVaultStats(vaultStatsRequest, SC_OK);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API get vault statistics by sub-sector.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsBySubsector() {
        // todo: limit = 1, by subsector
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder().build();
        VaultsApiClient.getVaultStats(vaultStatsRequest, SC_OK);
    }

    // todo: negative tests = null / empty / incorrect strings in the request
}
