package org.skopintsev.assertions.db.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.vaults.VaultDb;

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
}
