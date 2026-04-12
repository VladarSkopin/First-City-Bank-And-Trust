package org.skopintsev.assertions.api.clients;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.skopintsev.model.client.Client;

import java.util.List;

public class ClientApiAssertions {

    @Step("Check that clients list is not null.")
    public static void checkNotNullClients(List<Client> clients) {
        Assertions.assertThat(clients)
                .withFailMessage("Expected clients list to contain elements, but none were found.")
                .isNotNull()
                .isNotEmpty();
    }

}
