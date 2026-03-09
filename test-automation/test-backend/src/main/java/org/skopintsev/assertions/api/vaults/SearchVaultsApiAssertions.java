package org.skopintsev.assertions.api.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.model.vaults.Vault;

import java.util.Comparator;
import java.util.List;

public class SearchVaultsApiAssertions {

    @Step("Check search vault API response matches expected.")
    public static void checkSearchVaultsResponseMatchesExpected(List<Vault> actualVaults, List<Vault> expectedVaults) {

        Assertions.assertThat(actualVaults.size())
                .as("Number of vaults returned")
                .isEqualTo(expectedVaults.size());

        SoftAssertions.assertSoftly(softly -> {
            // Sort both lists by vault code for consistent comparison
            Comparator<Vault> byCode = Comparator.comparing(Vault::getVaultCode);
            List<Vault> sortedActual = actualVaults.stream().sorted(byCode).toList();
            List<Vault> sortedExpected = expectedVaults.stream().sorted(byCode).toList();

            softly.assertThat(sortedActual)
                    .as("Search results should match expected vaults")
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyElementsOf(sortedExpected);
        });
    }
}
