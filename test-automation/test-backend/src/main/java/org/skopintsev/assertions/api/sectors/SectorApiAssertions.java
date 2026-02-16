package org.skopintsev.assertions.api.sectors;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.sectors.Sector;

import java.util.List;

public class SectorApiAssertions {

    @Step("Check that sectors list is not null.")
    public static void checkNotNullSectors(List<Sector> sectors) {
        Assertions.assertThat(sectors)
                .withFailMessage("Expected sectors list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
