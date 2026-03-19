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
import org.skopintsev.models.api.Currency;
import org.skopintsev.models.api.District;
import org.skopintsev.models.api.SocialRank;
import org.skopintsev.util.LocalDateAdapter;


import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.skopintsev.constants.Api.*;
import static org.skopintsev.constants.Constants.*;

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
    public static void stubGet(String fullPath, Object responseObject, int statusCode) {
        WireMock.stubFor(
                WireMock.get(fullPath).willReturn(
                        WireMock.okJson(objectMapper.writeValueAsString(responseObject))
                                .withStatus(statusCode)
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Access-Control-Allow-Origin", "*")
                )
        );
    }

    @SneakyThrows
    public static void stubGetDefaultApi(String path, Object responseObject) {
        stubGet(path, responseObject, SC_OK);
    }

    @SneakyThrows
    public static void stubGetNotFoundApi(String path, Object responseObject) {
        stubGet(path, responseObject, SC_NOT_FOUND);
    }

    @SneakyThrows
    public static void stubGetServerErrorApi(String path, Object responseObject) {
        stubGet(path, responseObject, SC_SERVER_ERROR);
    }


    // Mocks

    @Step("POST /__admin/mappings: response for " + CURRENCIES)
    public static void stubGetCurrencies(List<Currency> currenciesList) {
        stubGetDefaultApi(CURRENCIES, currenciesList);
    }

    @Step("POST /__admin/mappings: response for " + CURRENCIES)
    public static void stubGetCurrenciesNotFound(List<Currency> currenciesList) {
        stubGetNotFoundApi(CURRENCIES, currenciesList);
    }

    @Step("POST /__admin/mappings: response for " + CURRENCIES)
    public static void stubGetCurrenciesServerError(List<Currency> currenciesList) {
        stubGetServerErrorApi(CURRENCIES, currenciesList);
    }

    @Step("POST /__admin/mappings: response for " + DISTRICTS)
    public static void stubGetDistricts(List<District> districtsList) {
        stubGetDefaultApi(DISTRICTS, districtsList);
    }

    @Step("POST /__admin/mappings: response for " + DISTRICTS)
    public static void stubGetDistrictsNotFound(List<District> districtsList) {
        stubGetNotFoundApi(DISTRICTS, districtsList);
    }

    @Step("POST /__admin/mappings: response for " + DISTRICTS)
    public static void stubGetDistrictsServerError(List<District> districtsList) {
        stubGetServerErrorApi(DISTRICTS, districtsList);
    }

    @Step("POST /__admin/mappings: response for " + SOCIAL_RANKS)
    public static void stubGetSocialRanks(List<SocialRank> socialRanksList) {
        stubGetDefaultApi(SOCIAL_RANKS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SOCIAL_RANKS)
    public static void stubGetSocialRanksNotFound(List<SocialRank> socialRanksList) {
        stubGetNotFoundApi(SOCIAL_RANKS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SOCIAL_RANKS)
    public static void stubGetSocialRanksServerError(List<SocialRank> socialRanksList) {
        stubGetServerErrorApi(SOCIAL_RANKS, socialRanksList);
    }

}
