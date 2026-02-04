package org.skopintsev.assertions.db.clients;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.clients.ClientDb;

public class ClientDbAssertions {

    @Step("Check client field {0}.")
    public static <T> void checkClientField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check client presence in the Database.")
    public static void checkClientPresence(ClientDb clientDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(clientDb)
                    .withFailMessage("Expected client to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(clientDb)
                    .withFailMessage("Expected client to be NULL in the Database.")
                    .isNull();
        }
    }
}
