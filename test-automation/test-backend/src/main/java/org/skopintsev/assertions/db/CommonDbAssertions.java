package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

public class CommonDbAssertions {

    @Step("Compare counts.")
    public static void checkCounts(Integer actualCount, Integer expectedCount) {
        Assertions.assertThat(actualCount)
                .withFailMessage("Expected count = " + expectedCount + ", but actual = " + actualCount)
                .isEqualTo(expectedCount);
    }

    @Step("Check how many rows were inserted.")
    public static void checkRowsInserted(Integer rowsInserted) {
        Assertions.assertThat(rowsInserted)
                .withFailMessage("Should insert 1 row")
                .isEqualTo(1);
    }
}
