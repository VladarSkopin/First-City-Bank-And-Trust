package org.skopintsev.assertions.api.vaults;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.Vault;

import java.util.List;

public class VaultApiAssertions {

    @Step("Check that vaults list is not null.")
    public static void checkNotNullVaults(List<Vault> vaults) {
        Assertions.assertThat(vaults)
                .withFailMessage("Expected vaults list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
