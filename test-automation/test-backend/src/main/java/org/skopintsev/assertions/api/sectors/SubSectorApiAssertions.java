package org.skopintsev.assertions.api.sectors;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.sectors.SubSector;

import java.util.List;

public class SubSectorApiAssertions {

    @Step("Check that sub-sectors list is not null.")
    public static void checkNotNullSubSectors(List<SubSector> subSectors) {
        Assertions.assertThat(subSectors)
                .withFailMessage("Expected sub-sectors list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
