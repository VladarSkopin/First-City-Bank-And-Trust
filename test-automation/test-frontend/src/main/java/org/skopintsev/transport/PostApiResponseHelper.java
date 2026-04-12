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
import org.skopintsev.models.api.*;
import org.skopintsev.models.api.client.Client;
import org.skopintsev.models.api.client.ClientType;
import org.skopintsev.models.api.sector.Sector;
import org.skopintsev.models.api.sector.SubSector;
import org.skopintsev.models.api.vault.Vault;
import org.skopintsev.models.api.vault.VaultOperationResponse;
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

    private static void stubOptions(String fullPath) {
        WireMock.stubFor(
                WireMock.options(WireMock.urlPathEqualTo(fullPath)).willReturn(
                        WireMock.ok()
                                .withHeader("Access-Control-Allow-Origin", "*")
                                .withHeader("Access-Control-Allow-Methods", "POST, OPTIONS")
                                .withHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
                                .withHeader("Access-Control-Allow-Credentials", "true")
                )
        );
    }

    @SneakyThrows
    private static void stubGet(String fullPath, Object responseObject, int statusCode) {
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
    private static void stubPost(String fullPath, Object responseObject, int statusCode) {
        WireMock.stubFor(
                WireMock.post(fullPath).willReturn(
                        WireMock.okJson(objectMapper.writeValueAsString(responseObject))
                                .withStatus(statusCode)
                                .withHeader("Content-Type", "application/json")
                                .withHeader("Access-Control-Allow-Origin", "*")
                )
        );
    }


    @SneakyThrows
    private static void stubGetDefaultApi(String path, Object responseObject) {
        stubGet(path, responseObject, SC_OK);
    }

    @SneakyThrows
    private static void stubGetNotFoundApi(String path, Object responseObject) {
        stubGet(path, responseObject, SC_NOT_FOUND);
    }

    @SneakyThrows
    private static void stubGetServerErrorApi(String path, Object responseObject) {
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

    @Step("POST /__admin/mappings: response for " + SECTORS)
    public static void stubGetSectors(List<Sector> socialRanksList) {
        stubGetDefaultApi(SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SECTORS)
    public static void stubGetSectorsNotFound(List<Sector> socialRanksList) {
        stubGetNotFoundApi(SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SECTORS)
    public static void stubGetSectorsServerError(List<Sector> socialRanksList) {
        stubGetServerErrorApi(SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SUB_SECTORS)
    public static void stubGetSubSectors(List<SubSector> socialRanksList) {
        stubGetDefaultApi(SUB_SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SUB_SECTORS)
    public static void stubGetSubSectorsNotFound(List<SubSector> socialRanksList) {
        stubGetNotFoundApi(SUB_SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + SUB_SECTORS)
    public static void stubGetSubSectorsServerError(List<SubSector> socialRanksList) {
        stubGetServerErrorApi(SUB_SECTORS, socialRanksList);
    }

    @Step("POST /__admin/mappings: response for " + CLIENT_TYPES)
    public static void stubGetClientTypes(List<ClientType> clientTypesList) {
        stubGetDefaultApi(CLIENT_TYPES, clientTypesList);
    }

    @Step("POST /__admin/mappings: response for " + CLIENTS)
    public static void stubGetClients(List<Client> clientsList) {
        stubGetDefaultApi(CLIENTS, clientsList);
    }

    @Step("POST /__admin/mappings: response for " + CLIENTS)
    public static void stubGetClientsNotFound(List<Client> clientsList) {
        stubGetNotFoundApi(CLIENTS, clientsList);
    }

    @Step("POST /__admin/mappings: response for " + CLIENTS)
    public static void stubGetClientsServerError(List<Client> clientsList) {
        stubGetServerErrorApi(CLIENTS, clientsList);
    }

    @Step("POST /__admin/mappings: response for " + VAULTS)
    public static void stubGetVaults(List<Vault> vaultsList) {
        stubGetDefaultApi(VAULTS, vaultsList);
    }

    @Step("POST /__admin/mappings: response for " + VAULTS)
    public static void stubGetVaultsNotFound(List<Vault> vaultsList) {
        stubGetNotFoundApi(VAULTS, vaultsList);
    }

    @Step("POST /__admin/mappings: response for " + VAULTS)
    public static void stubGetVaultsServerError(List<Vault> vaultsList) {
        stubGetServerErrorApi(VAULTS, vaultsList);
    }

    @Step("POST /__admin/mappings: response for " + VAULT_OPERATIONS)
    public static void stubPostVaultOperation(VaultOperationResponse response) {
        stubOptions(VAULT_OPERATIONS);
        stubPost(VAULT_OPERATIONS, response, 200);
    }
}
