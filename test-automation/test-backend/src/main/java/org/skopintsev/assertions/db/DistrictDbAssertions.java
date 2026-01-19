package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.district.DistrictDb;

public class DistrictDbAssertions {
    @Step("Check district name.")
    public static void checkDistrictName(String actualDistrictName, String expectedDistrictName) {
        Assertions.assertThat(actualDistrictName)
                .withFailMessage("Expected district name = " + expectedDistrictName + ", but actual = " + actualDistrictName)
                .isEqualTo(expectedDistrictName);
    }

    @Step("Check district presence in the Database.")
    public static void checkDistrictPresence(DistrictDb districtDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(districtDb)
                    .withFailMessage("Expected district to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(districtDb)
                    .withFailMessage("Expected district to be NULL in the Database.")
                    .isNull();
        }
    }
}
