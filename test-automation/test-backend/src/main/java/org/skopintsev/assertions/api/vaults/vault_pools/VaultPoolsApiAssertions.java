package org.skopintsev.assertions.api.vaults.vault_pools;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.model.vaults.vault_pools.VaultPool;
import org.skopintsev.model.vaults.vault_pools.VaultPoolResponse;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class VaultPoolsApiAssertions {

    @Step("Check vault pools list API response matches expected.")
    public static void checkVaultPoolsResponseMatchesExpected(
            List<VaultPoolResponse> vaultPoolResponseListActual, List<VaultPoolResponse> vaultPoolResponseListExpected) {

        Assertions.assertThat(vaultPoolResponseListActual.size())
                .as("Number of vault pools returned")
                .isEqualTo(vaultPoolResponseListExpected.size());

        // Sort both lists by vault pool name for consistent comparison
        Comparator<VaultPoolResponse> byName = Comparator.comparing(VaultPoolResponse::getVaultPoolName);
        List<VaultPoolResponse> sortedActual = vaultPoolResponseListActual.stream().sorted(byName).toList();
        List<VaultPoolResponse> sortedExpected = vaultPoolResponseListExpected.stream().sorted(byName).toList();

        SoftAssertions.assertSoftly(softly -> {
            for (int i = 0; i < sortedActual.size(); i++) {
                VaultPoolResponse actual = sortedActual.get(i);
                VaultPoolResponse expected = sortedExpected.get(i);

                // Compare all fields except datetimes
                softly.assertThat(actual)
                        .as("Vault pool at index %d (name: %s)", i, actual.getVaultPoolName())
                        .usingRecursiveComparison()
                        .ignoringFields("createdFrom", "createdTo")
                        .isEqualTo(expected);

                // Compare datetimes with tolerance
                checkVaultPoolsTime(actual.getCreatedFrom(), expected.getCreatedFrom(),
                        "createdFrom", actual.getVaultPoolName(), softly);
                checkVaultPoolsTime(actual.getCreatedTo(), expected.getCreatedTo(),
                        "createdTo", actual.getVaultPoolName(), softly);
            }
        });
    }

    @Step("Check vault pool '{dateTimeField}' for pool '{vaultPoolName}' is within expected period.")
    public static void checkVaultPoolsTime(
            LocalDateTime actualDateTime,
            LocalDateTime expectedDateTime,
            String dateTimeField,
            String vaultPoolName,
            SoftAssertions softly) {

        if (expectedDateTime == null) {
            softly.assertThat(actualDateTime)
                    .as("vault_pool '%s' %s should be null", vaultPoolName, dateTimeField)
                    .isNull();
        } else {
            softly.assertThat(actualDateTime)
                    .as("vault_pool '%s' %s = %s, expected near %s",
                            vaultPoolName, dateTimeField, actualDateTime, expectedDateTime)
                    .isBetween(expectedDateTime.minusMinutes(1), expectedDateTime.plusMinutes(1));
        }
    }

    @Step("Check vault pool API response object matches expected.")
    public static void checkVaultPoolMatchesExpected(VaultPool vaultPoolActual, VaultPool vaultPoolExpected) {
        SoftAssertions.assertSoftly(softly -> {
                // Compare all fields except datetimes
                softly.assertThat(vaultPoolActual)
                        .as("Vault pool API (name: %s)", vaultPoolActual.getVaultPoolName())
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdFrom", "createdTo")
                        .isEqualTo(vaultPoolExpected);

                // Compare datetimes with tolerance
                checkVaultPoolsTime(vaultPoolActual.getCreatedFrom(), vaultPoolExpected.getCreatedFrom(),
                        "createdFrom", vaultPoolActual.getVaultPoolName(), softly);
                checkVaultPoolsTime(vaultPoolActual.getCreatedTo(), vaultPoolExpected.getCreatedTo(),
                        "createdTo", vaultPoolActual.getVaultPoolName(), softly);
        });
    }
}
