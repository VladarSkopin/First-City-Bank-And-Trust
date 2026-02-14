package org.skopintsev.assertions.db.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.database.vaults.VaultDb;
import org.skopintsev.model.Vault;

public class VaultDbAssertions {

    @Step("Check vault field {0}.")
    public static <T> void checkVaultField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check vault presence in the Database.")
    public static void checkVaultPresence(VaultDb vaultDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(vaultDb)
                    .withFailMessage("Expected vault to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(vaultDb)
                    .withFailMessage("Expected vault to be NULL in the Database.")
                    .isNull();
        }
    }

    @Step("Check vault API object matches Database object.")
    public static void checkVaultMatchesDb(Vault vault, VaultDb vaultDb) {
        SoftAssertions.assertSoftly(
                softly -> {
                    softly
                            .assertThat(vault.getClientCode())
                            .as("clientCode")
                            .isEqualTo(vaultDb.getClientCode());

                    softly.assertThat(vault.getAmount())
                            .as("amount")
                            .isEqualTo(vaultDb.getAmount());

                    softly.assertThat(vault.getCurrencyCode())
                            .as("currencyCode")
                            .isEqualTo(vaultDb.getCurrencyCode());

                    softly.assertThat(vault.getIsArchived())
                            .as("isArchived")
                            .isEqualTo(vaultDb.getIsArchived());
                }
        );


    }
}
