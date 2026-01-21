package org.skopintsev.assertions.db;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.database.social_ranks.SocialRankDb;

public class SocialRankDbAssertions {

    @Step("Check social rank field {0}.")
    public static <T> void checkSocialRankField(String fieldName, T actualValue, T expectedValue) {
        Assertions.assertThat(actualValue)
                .withFailMessage("Expected '%s' = '%s', but actual = '%s'", fieldName, expectedValue, actualValue)
                .isEqualTo(expectedValue);
    }

    @Step("Check social rank presence in the Database.")
    public static void checkSocialRankPresence(SocialRankDb socialRankDb, boolean shouldBePresent) {
        if (shouldBePresent) {
            Assertions.assertThat(socialRankDb)
                    .withFailMessage("Expected social rank to be NOT NULL in the Database.")
                    .isNotNull();
        } else {
            Assertions.assertThat(socialRankDb)
                    .withFailMessage("Expected social rank to be NULL in the Database.")
                    .isNull();
        }
    }
}
