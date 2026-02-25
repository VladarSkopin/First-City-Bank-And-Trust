package org.skopintsev.assertions.api.clients;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.skopintsev.model.Client;

import java.util.Comparator;
import java.util.List;

public class SearchClientsApiAssertions {

    @Step("Check search client API response matches expected.")
    public static void checkSearchClientsResponseMatchesExpected(List<Client> actualClients, List<Client> expectedClients) {

        Assertions.assertThat(actualClients.size())
                .as("Number of clients returned")
                .isEqualTo(expectedClients.size());

        SoftAssertions.assertSoftly(softly -> {
            // Sort both lists by client code for consistent comparison
            Comparator<Client> byCode = Comparator.comparing(Client::getClientCode);
            List<Client> sortedActual = actualClients.stream().sorted(byCode).toList();
            List<Client> sortedExpected = expectedClients.stream().sorted(byCode).toList();

            softly.assertThat(sortedActual)
                    .as("Search results should match expected clients")
                    .usingRecursiveFieldByFieldElementComparator()
                    .containsExactlyElementsOf(sortedExpected);
        });
    }

    @Step("Check search client API response is empty.")
    public static void checkSearchClientsResponseListContent(List<Client> clients, boolean shouldBeEmpty) {
        if (!shouldBeEmpty) {
            Assertions.assertThat(clients)
                    .withFailMessage("Expected clients list to be NOT NULL in the response.")
                    .isNotNull();
        } else {
            Assertions.assertThat(clients)
                    .withFailMessage("Expected clients list to be NULL in the response.")
                    .isNull();
        }
    }
}
