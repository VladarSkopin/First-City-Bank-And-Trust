package org.skopintsev.transport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.skopintsev.models.Currency;
import org.skopintsev.util.LocalDateAdapter;


import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.skopintsev.constants.Api.CURRENCIES;
import static org.skopintsev.constants.Constants.SC_OK;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostApiResponseHelper {

    static final ObjectMapper objectMapper = JsonMapper.builder()
            // Disables alphabetical sorting of JSON properties - to appear in the order they're defined in the class
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
            .build()
            // Excludes null values from JSON output - fields with null values omitted entirely
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            // Prevents writing dates as timestamps - "2024-03-15" (ISO strings) instead of 1700000000000
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // All LocalDate objects will use custom formatting
            .registerModule(new SimpleModule().addSerializer(LocalDate.class, new LocalDateAdapter()))
            // Adds support for Java 8+ Date/Time API - LocalDate, LocalDateTime, ZonedDateTime, Instant, etc.
            .registerModule(new JavaTimeModule())
            // Without this, Jackson would only serialize public fields/getters of ResponseDefinitionBuilder
            // When serializing ResponseDefinitionBuilder objects, all fields are visible
            .addMixIn(ResponseDefinitionBuilder.class, ResponseDefinitionBuilderMixIn.class);

    @Step("DELETE /__admin/mappings. Delete all mappings in WireMock.")
    public static void deleteMappingsJournal() {
        WireMock.removeAllMappings();
    }

    @Step("DELETE /__admin/requests. Delete all incoming requests in WireMock journal.")
    public static void deleteWebRequestsJournal() {
        WireMock.resetAllRequests();
    }

    @Step("POST /__admin/reset. Reset all defined responses in WireMock.")
    public static void postResetMock() {
        WireMock.reset();
    }

    @Step("GET /__admin/mappings. Request for getting all defined responses in WireMock.")
    public static void getMappingsMock() {
        WireMock.stubFor(WireMock.get("/__admin/mappings").willReturn(ok()));
    }

    @SneakyThrows
    public static void stubPostDefaultApi(String path, Object responseObject) {
        stubPostDefaultApi(path, responseObject, SC_OK);
    }

    @SneakyThrows
    public static void stubPostDefaultApi(String path, Object responseObject, int statusCode) {
        stubPost(path, responseObject, statusCode);
    }

    @SneakyThrows
    public static void stubPost(String fullPath, Object responseObject, int statusCode) {
        WireMock.stubFor(
                WireMock.post(fullPath).willReturn(
                        WireMock.okJson(objectMapper.writeValueAsString(responseObject)).withStatus(statusCode)
                )
        );
    }


    // Mocks

    @Step(" .")
    public static void postGetCurrencies(List<Currency> currenciesList) {
        stubPostDefaultApi(CURRENCIES, currenciesList);
    }


}
