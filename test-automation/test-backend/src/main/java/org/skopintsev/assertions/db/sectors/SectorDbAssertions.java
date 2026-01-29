package org.skopintsev.assertions.db.sectors;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.sectors.SectorDb;

public class SectorDbAssertions {

    @Step("Check sector field {0}.")
    public static <T> void checkSectorField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check sector presence in the Database.")
    public static void checkSectorPresence(SectorDb sectorDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(sectorDb)
                    .withFailMessage("Expected sector to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(sectorDb)
                    .withFailMessage("Expected sector to be NULL in the Database.")
                    .isNull();
        }
    }
}
