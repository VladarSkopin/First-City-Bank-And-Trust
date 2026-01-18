package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

public class DistrictDbAssertions {
    @Step("Check currency name.")
    public static void checkDistrictName(String actualDistrictName, String expectedDistrictName) {
        Assertions.assertThat(actualDistrictName)
                .withFailMessage("Expected district name = " + expectedDistrictName + ", but actual = " + actualDistrictName)
                .isEqualTo(expectedDistrictName);
    }
}
