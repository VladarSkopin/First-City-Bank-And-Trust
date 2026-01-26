package org.skopintsev.assertions.api;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.District;

import java.util.List;

public class DistrictApiAssertions {

    @Step("Check that districts list is not null.")
    public static void checkNotNullDistricts(List<District> districts) {
        Assertions.assertThat(districts)
                .withFailMessage("Expected districts list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
