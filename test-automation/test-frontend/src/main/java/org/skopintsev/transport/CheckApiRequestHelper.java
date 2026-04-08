package org.skopintsev.transport;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.awaitility.Awaitility;
import org.skopintsev.models.api.vault.VaultOperationRequest;
import org.skopintsev.util.LocalDateAdapter;


import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.skopintsev.constants.Api.VAULT_OPERATIONS;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckApiRequestHelper {

    static final ObjectMapper objectMapper = JsonMapper.builder()
            // Disables alphabetical sorting of JSON properties - to appear in the order they're defined in the class
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
            .build()
            // Excludes null values from JSON output - fields with null values omitted entirely
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            // All LocalDate objects will use custom formatting
            .registerModule(new SimpleModule().addSerializer(LocalDate.class, new LocalDateAdapter()));



    private static void checkRequestBodyFound(String requestBodyToFind, String contextReq, boolean ignoreExtraElements,
                                              int callsCount) {
        WireMock.verify(
                callsCount,
                WireMock.postRequestedFor(
                        WireMock.urlEqualTo(contextReq))
                        .withRequestBody(
                                WireMock.equalToJson(requestBodyToFind, true, ignoreExtraElements))
        );
    }

    @SneakyThrows
    private static void checkRequestFoundByContainsJson(Object requestObject, String contextReq) {
        checkRequestBodyFound(objectMapper.writeValueAsString(requestObject), contextReq, true, 1);
    }

    @SneakyThrows
    private static void checkRequestNotFoundByContainsJson(String contextReq) {
        WireMock.verify(
                0,
                WireMock.postRequestedFor(
                        WireMock.urlEqualTo(contextReq)
                )
        );
    }

    @SneakyThrows
    private static void checkRequestNotFoundByContainsJson(Object requestObject, String contextReq) {
        WireMock.verify(
                0,
                WireMock.postRequestedFor(
                        WireMock.urlEqualTo(contextReq))
                        .withRequestBody(
                                WireMock.equalToJson(objectMapper.writeValueAsString(requestObject),
                                        true,
                                        true)
                        )
        );
    }

    @SneakyThrows
    private static void checkRequestBodyEmpty(String contextReq) {
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .untilAsserted(
                        () -> {
                            WireMock.verify(
                                    WireMock.postRequestedFor(
                                            WireMock.urlEqualTo(contextReq)
                                    )
                            );
                        }
                );
    }

    @Step("Check that request '{0}' was sent with a correct body using matchesJsonPath pattern.")
    public static void checkRequestFoundByMatchesJsonPath(String contextReq, String patternToMatch) {
        WireMock.verify(
                1,
                WireMock.postRequestedFor(
                        WireMock.urlEqualTo(contextReq))
                        .withRequestBody(WireMock.matchingJsonPath(patternToMatch))
        );
    }

    @Step("Check that GET request '{0}' was sent successfully from the web.")
    public static void checkGetSuccessfulSendRequests(String contextReq) {
        WireMock.verify(
                1,
                WireMock.getRequestedFor(
                        WireMock.urlEqualTo(contextReq)
                )
        );
    }


    @Step("Check that " + VAULT_OPERATIONS + " request was sent from the web correctly.")
    public static void checkRequestFoundByContainsJson(VaultOperationRequest request) {
        checkRequestFoundByContainsJson(request, VAULT_OPERATIONS);
    }

    @Step("Check that " + VAULT_OPERATIONS + " request was NOT sent from the web correctly.")
    public static void checkRequestNotFoundByContainsJson(VaultOperationRequest request) {
        checkRequestNotFoundByContainsJson(request, VAULT_OPERATIONS);
    }

}
