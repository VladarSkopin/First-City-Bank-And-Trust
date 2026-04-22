package org.skopintsev.assertions.db.vaults.vault_pools;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.database.vaults.VaultPoolsDb;

import java.time.LocalDateTime;

public class VaultPoolsDbAssertions {

    @Step("Check vault pool database object matches expected.")
    public static void checkVaultPoolDbMatchesExpected(
            VaultPoolsDb vaultPoolsDbActual, VaultPoolsDb vaultPoolsDbExpected) {

        SoftAssertions.assertSoftly(softly -> {
                // Compare all fields except id and datetimes
                softly.assertThat(vaultPoolsDbActual)
                        .as("Vault pool Db (name: %s)", vaultPoolsDbActual.getVaultPoolName())
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdFrom", "createdTo")
                        .isEqualTo(vaultPoolsDbExpected);

                // Compare datetimes with tolerance
                checkVaultPoolsTime(vaultPoolsDbActual.getCreatedFrom(), vaultPoolsDbExpected.getCreatedFrom(),
                        "createdFrom", vaultPoolsDbActual.getVaultPoolName(), softly);
                checkVaultPoolsTime(vaultPoolsDbActual.getCreatedTo(), vaultPoolsDbExpected.getCreatedTo(),
                        "createdTo", vaultPoolsDbActual.getVaultPoolName(), softly);

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
}
