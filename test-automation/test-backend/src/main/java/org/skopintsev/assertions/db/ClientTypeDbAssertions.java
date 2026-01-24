package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.client_types.ClientTypeDb;

public class ClientTypeDbAssertions {

    @Step("Check client type field {0}.")
    public static <T> void checkClientTypeField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check client type presence in the Database.")
    public static void checkClientTypePresence(ClientTypeDb clientTypeDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(clientTypeDb)
                    .withFailMessage("Expected client type to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(clientTypeDb)
                    .withFailMessage("Expected client type to be NULL in the Database.")
                    .isNull();
        }
    }
}
