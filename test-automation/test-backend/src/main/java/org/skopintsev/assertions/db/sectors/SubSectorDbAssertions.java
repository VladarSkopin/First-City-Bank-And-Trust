package org.skopintsev.assertions.db.sectors;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.sectors.subsectors.SubSectorDb;

public class SubSectorDbAssertions {

    @Step("Check sub-sector field {0}.")
    public static <T> void checkSubSectorField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check sub-sector presence in the Database.")
    public static void checkSubSectorPresence(SubSectorDb subSectorDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(subSectorDb)
                    .withFailMessage("Expected sub-sector to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(subSectorDb)
                    .withFailMessage("Expected sub-sector to be NULL in the Database.")
                    .isNull();
        }
    }
}
