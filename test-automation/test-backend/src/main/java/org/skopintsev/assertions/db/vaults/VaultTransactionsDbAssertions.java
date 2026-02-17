package org.skopintsev.assertions.db.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.database.vaults.VaultTransactionsDb;

import java.time.LocalDateTime;

public class VaultTransactionsDbAssertions {

    @Step("Check vault transaction record from the Database matches the expected parameters.")
    public static void checkVaultTransactionDbMatchesExpectedParams(
            VaultTransactionsDb actualVaultTransactionDb, VaultTransactionsDb expectedVaultTransactionDb) {
        SoftAssertions.assertSoftly(
                softly -> {
                    softly
                            .assertThat(actualVaultTransactionDb)
                            .as("VaultTransactionDb parameters match the expected ones")
                            .usingRecursiveComparison()
                            .ignoringFields("transactionId", "transactionTime")
                            .isEqualTo(expectedVaultTransactionDb);
                }
        );
    }

    @Step("Check vault transaction time from the Database is within expected period.")
    public static void checkVaultTransactionDbTime(
            LocalDateTime actualTransactionTime, LocalDateTime expectedTransactionTime) {
        Assertions.assertThat(actualTransactionTime)
                .withFailMessage(
                        "vault_transactions.transaction_time = "
                        + actualTransactionTime
                        + " but expected = "
                        + expectedTransactionTime)
                .isBetween(expectedTransactionTime, expectedTransactionTime.plusMinutes(2L));
    }
}
