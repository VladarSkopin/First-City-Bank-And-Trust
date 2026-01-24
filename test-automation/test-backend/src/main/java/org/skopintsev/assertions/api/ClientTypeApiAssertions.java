package org.skopintsev.assertions.api;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.ClientType;

import java.util.List;

public class ClientTypeApiAssertions {

    @Step("Check that client types list is not null.")
    public static void checkNotNullClientTypes(List<ClientType> clientTypes) {
        Assertions.assertThat(clientTypes)
                .withFailMessage("Expected client types list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }
}
