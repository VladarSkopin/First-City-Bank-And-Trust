package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.districts.DistrictDb;

public class DistrictDbAssertions {

    @Step("Check district code.")
    public static void checkDistrictCode(String actualDistrictCode, String expectedDistrictCode) {
        Assertions.assertThat(actualDistrictCode)
                .withFailMessage("Expected district code = '%s', but actual = '%s'", expectedDistrictCode, actualDistrictCode)
                .isEqualTo(expectedDistrictCode);
    }

    @Step("Check district name.")
    public static void checkDistrictName(String actualDistrictName, String expectedDistrictName) {
        Assertions.assertThat(actualDistrictName)
                .withFailMessage("Expected district name = '%s', but actual = '%s'", expectedDistrictName, actualDistrictName)
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
