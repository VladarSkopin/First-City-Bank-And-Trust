package vault;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skopintsev.assertions.api.vaults.VaultStatsApiAssertions;
import org.skopintsev.database.factory.VaultDbFactory;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.database.vaults.VaultDbHelper;
import org.skopintsev.helper.enums.SearchByEnum;
import org.skopintsev.model.vaults.vaultstats.SearchParams;
import org.skopintsev.model.vaults.vaultstats.VaultStatsRequest;
import org.skopintsev.model.vaults.vaultstats.VaultStatsResponse;
import org.skopintsev.model.vaults.vaultstats.VaultSummary;
import org.skopintsev.transport.api.VaultsApiClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.skopintsev.constants.Constants.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VaultStatsTest extends BaseVaultTest {

    final int LIMIT = 5;
    final int LIMIT_SINGLE = 1;
    final SearchParams SEARCH_PARAMS = SearchParams.builder()
            .searchBy(SearchByEnum.SUBSECTOR.getText())
            .searchString(BASE_SUB_SECTOR_CODE)
            .build();
    final VaultStatsRequest VAULT_STATS_REQUEST = VaultStatsRequest.builder()
            .limit(LIMIT)
            .searchParams(SEARCH_PARAMS)
            .systemName(SYSTEM_NAME_CORE)
            .build();

    @Test
    @Tag("regression")
    @Description("Test uses API to get vault statistics when there are more vaults than requested limit.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsMoreThanLimit() {
        List<VaultDb> vaults = insertMultipleDefaultVaults(6, BASE_CLIENT_CODE, BASE_CURRENCY_CODE);
        List<VaultSummary> vaultSummaryListExpected = toVaultSummaries(vaults);
        vaultSummaryListExpected.remove(vaultSummaryListExpected.size() - 1);

        VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(VAULT_STATS_REQUEST, SC_OK);
        VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), LIMIT);
        VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
        VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
        VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                vaultStatsResponse.getVaults(), vaultSummaryListExpected);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to get vault statistics when there are vaults equal to the requested limit.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsEqualToLimit() {
        List<VaultDb> vaults = insertMultipleDefaultVaults(5, BASE_CLIENT_CODE, BASE_CURRENCY_CODE);
        List<VaultSummary> vaultSummaryListExpected = toVaultSummaries(vaults);

        VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(VAULT_STATS_REQUEST, SC_OK);
        VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), LIMIT);
        VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
        VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
        VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                vaultStatsResponse.getVaults(), vaultSummaryListExpected);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to get vault statistics when there are less vaults than requested limit.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsLessThanLimit() {
        List<VaultDb> vaults = insertMultipleDefaultVaults(4, BASE_CLIENT_CODE, BASE_CURRENCY_CODE);
        List<VaultSummary> vaultSummaryListExpected = toVaultSummaries(vaults);

        VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(VAULT_STATS_REQUEST, SC_OK);
        VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), 4);
        VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
        VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
        VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                vaultStatsResponse.getVaults(), vaultSummaryListExpected);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to get vault statistics by sub-sector.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsBySector() {
        VaultDb vaultDb = VaultDbFactory.defaultVaultDbRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        VaultDbHelper.insertVault(vaultDb);

        List<VaultSummary> vaultSummaryListExpected = List.of(
                VaultSummary.builder()
                        .vaultCode(vaultDb.getVaultCode())
                        .amount(vaultDb.getAmount())
                        .currency(vaultDb.getCurrencyCode())
                        .build());

        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder()
                .limit(LIMIT_SINGLE)
                .searchParams(SEARCH_PARAMS)
                .systemName(SYSTEM_NAME_CORE)
                .build();
        VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_OK);
        VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), LIMIT_SINGLE);
        VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
        VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
        VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                vaultStatsResponse.getVaults(), vaultSummaryListExpected);
    }

    @Test
    @Tag("regression")
    @Description("Test uses API to get vault statistics by sector.")
    @Severity(SeverityLevel.NORMAL)
    public void getVaultStatsBySubSector() {
        VaultDb vaultDb = VaultDbFactory.defaultVaultDbRequest(
                BASE_CLIENT_CODE,
                BASE_CURRENCY_CODE
        );
        VaultDbHelper.insertVault(vaultDb);

        List<VaultSummary> vaultSummaryListExpected = List.of(
                VaultSummary.builder()
                        .vaultCode(vaultDb.getVaultCode())
                        .amount(vaultDb.getAmount())
                        .currency(vaultDb.getCurrencyCode())
                        .build());

        SearchParams searchParams = SearchParams.builder()
                .searchBy(SearchByEnum.SECTOR.getText())
                .searchString(BASE_SECTOR_CODE)
                .build();
        VaultStatsRequest vaultStatsRequest = VaultStatsRequest.builder()
                .limit(LIMIT_SINGLE)
                .searchParams(searchParams)
                .systemName(SYSTEM_NAME_CORE)
                .build();
        VaultStatsResponse vaultStatsResponse = VaultsApiClient.getVaultStatsAndValidate(vaultStatsRequest, SC_OK);
        VaultStatsApiAssertions.checkVaultStatsTotalCountResponse(vaultStatsResponse.getTotalCount(), LIMIT_SINGLE);
        VaultStatsApiAssertions.checkVaultStatsSystemNameResponse(vaultStatsResponse.getSystemName(), SYSTEM_NAME_VAULTS);
        VaultStatsApiAssertions.checkVaultStatsDateReceivedResponse(
                vaultStatsResponse.getDateReceived(), LocalDateTime.now().minusMinutes(1L));
        VaultStatsApiAssertions.checkVaultStatsSummaryResponseMatchesExpected(
                vaultStatsResponse.getVaults(), vaultSummaryListExpected);
    }


    private List<VaultDb> insertMultipleDefaultVaults(int count, String clientCode, String currencyCode) {
        List<VaultDb> inserted = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            VaultDb vault = VaultDbFactory.defaultVaultDbRequest(clientCode, currencyCode);
            VaultDbHelper.insertVault(vault);
            inserted.add(vault);
        }
        return inserted;
    }

    private List<VaultSummary> toVaultSummaries(List<VaultDb> vaults) {
        return vaults.stream()
                .map(v -> VaultSummary.builder()
                        .vaultCode(v.getVaultCode())
                        .amount(v.getAmount())
                        .currency(v.getCurrencyCode())
                        .build())
                .collect(Collectors.toList());
    }
}
