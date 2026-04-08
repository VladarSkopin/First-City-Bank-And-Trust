package org.skopintsev.assertions.api.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.model.vaults.vaultstats.VaultSummary;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class VaultStatsApiAssertions {

    @Step("Check vault stats total count API response matches expected.")
    public static void checkVaultStatsTotalCountResponse(
            int totalCountActual, int totalCountExpected) {

        Assertions.assertThat(totalCountActual)
                .withFailMessage("Expected actual total count [" + totalCountActual + "] to be equal to "
                        + totalCountExpected)
                .isEqualTo(totalCountExpected);
    }

    @Step("Check vault stats system name API response matches expected.")
    public static void checkVaultStatsSystemNameResponse(
            String systemNameActual, String systemNameExpected) {

        Assertions.assertThat(systemNameActual)
                .withFailMessage("Expected actual system name [" + systemNameActual + "] to be equal to "
                        + systemNameExpected)
                .isEqualTo(systemNameExpected);
    }

    // check LocalDateTime dateReceived
    @Step("Check vault stats date received API response matches expected.")
    public static void checkVaultStatsDateReceivedResponse(
            LocalDateTime dateReceivedActual, LocalDateTime dateReceivedExpected) {

        Assertions.assertThat(dateReceivedActual)
                .as("dateReceived = " + dateReceivedActual + " but expected = "
                        + dateReceivedExpected)
                .isBetween(dateReceivedExpected, dateReceivedExpected.plusMinutes(2L));
    }

    @Step("Check vault stats summary list API response matches expected.")
    public static void checkVaultStatsSummaryResponseMatchesExpected(
            List<VaultSummary> vaultSummaryListActual, List<VaultSummary> vaultSummaryListExpected) {

        Assertions.assertThat(vaultSummaryListActual.size())
                .as("Number of vault summaries returned")
                .isEqualTo(vaultSummaryListExpected.size());

        SoftAssertions.assertSoftly(softly -> {
            // Sort both lists by vault code for consistent comparison
            Comparator<VaultSummary> byCode = Comparator.comparing(VaultSummary::getVaultCode);
            List<VaultSummary> sortedActual = vaultSummaryListActual.stream().sorted(byCode).toList();
            List<VaultSummary> sortedExpected = vaultSummaryListExpected.stream().sorted(byCode).toList();

            softly.assertThat(sortedActual)
                    .as("Search results should match expected vault summaries")
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyElementsOf(sortedExpected);
        });
    }
}
