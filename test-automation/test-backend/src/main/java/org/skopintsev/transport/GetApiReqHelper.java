package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.*;
import org.skopintsev.model.sectors.Sector;
import org.skopintsev.model.sectors.SubSector;
import org.skopintsev.model.vaults.Vault;

import java.util.List;


public class GetApiReqHelper {

//    @Step("GET " + Api.CURRENCIES + " with expected status code {0}")
//    public static List<Currency> getCurrenciesAndValidate(int expectedStatusCode) {
//        Response response = CommonApiReqHelper.getRequest(Api.CURRENCIES);
//        response.then().statusCode(expectedStatusCode);
//
//        return response.as(new TypeRef<List<Currency>>() {});
//    }

//    @Step("GET " + Api.DISTRICTS + " with expected status code {0}")
//    public static List<District> getDistrictsAndValidate(int expectedStatusCode) {
//        Response response = CommonApiReqHelper.getRequest(Api.DISTRICTS);
//        response.then().statusCode(expectedStatusCode);
//
//        return response.as(new TypeRef<List<District>>() {});
//    }

//    @Step("GET " + Api.SOCIAL_RANKS + " with expected status code {0}")
//    public static List<SocialRank> getSocialRanksAndValidate(int expectedStatusCode) {
//        Response response = CommonApiReqHelper.getRequest(Api.SOCIAL_RANKS);
//        response.then().statusCode(expectedStatusCode);
//
//        return response.as(new TypeRef<List<SocialRank>>() {});
//    }

//    @Step("GET " + Api.CLIENT_TYPES + " with expected status code {0}")
//    public static List<ClientType> getClientTypesAndValidate(int expectedStatusCode) {
//        Response response = CommonApiReqHelper.getRequest(Api.CLIENT_TYPES);
//        response.then().statusCode(expectedStatusCode);
//
//        return response.as(new TypeRef<List<ClientType>>() {});
//    }

    @Step("GET " + Api.SECTORS + " with expected status code {0}")
    public static List<Sector> getSectorsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Sector>>() {});
    }

    @Step("GET " + Api.SUB_SECTORS + " with expected status code {0}")
    public static List<SubSector> getSubSectorsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SUB_SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<SubSector>>() {});
    }

    @Step("GET " + Api.CLIENTS + " with expected status code {0}")
    public static List<Client> getClientsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.CLIENTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.VAULTS + " with expected status code {0}")
    public static List<Vault> getVaultsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.VAULTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS_BY_SOCIAL_RANK + " with expected status code {0}")
    public static List<Client> searchClientsByRankAndValidate(String socialRankCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_CLIENTS_BY_SOCIAL_RANK, socialRankCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS_BY_CLIENT_TYPE + " with expected status code {0}")
    public static List<Client> searchClientsByClientTypeAndValidate(String clientTypeCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_CLIENTS_BY_CLIENT_TYPE, clientTypeCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS_BY_SUB_SECTOR + " with expected status code {0}")
    public static List<Client> searchClientsBySubSectorAndValidate(String subSectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_CLIENTS_BY_SUB_SECTOR, subSectorCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS_BY_SECTOR + " with expected status code {0}")
    public static List<Client> searchClientsBySectorAndValidate(String sectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_CLIENTS_BY_SECTOR, sectorCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS_BY_DISTRICT + " with expected status code {0}")
    public static List<Client> searchClientsByDistrictAndValidate(String districtCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_CLIENTS_BY_DISTRICT, districtCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.SEARCH_CLIENTS + " with multiple query parameters")
    public static List<Client> searchClientsWithParamsAndValidate(
            String clientTypeCode,
            String districtCode,
            String subSectorCode,
            int expectedStatusCode) {

        Response response = CommonApiReqHelper.getRequestWithQueryParams(
                Api.SEARCH_CLIENTS,
                "clientTypeCode", clientTypeCode,
                "districtCode", districtCode,
                "subSectorCode", subSectorCode
        );

        response.then().statusCode(expectedStatusCode);
        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_ALL + " with expected status code {0}")
    public static Integer countAllClientsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_ALL);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_ACTIVE + " with expected status code {0}")
    public static Integer countActiveClientsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_ACTIVE);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_BLOCKED + " with expected status code {0}")
    public static Integer countBlockedClientsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_BLOCKED);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_BY_RANK + " with expected status code {0}")
    public static Integer countClientsByRankAndValidate(String rankCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_BY_RANK, rankCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_BY_TYPE + " with expected status code {0}")
    public static Integer countClientsByClientTypeAndValidate(String clientTypeCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_BY_TYPE, clientTypeCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.COUNT_CLIENTS_BY_SECTOR + " with expected status code {0}")
    public static Integer countClientsBySectorAndValidate(String sectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.COUNT_CLIENTS_BY_SECTOR, sectorCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<Integer>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CURRENCY + " with expected status code {0}")
    public static List<Vault> searchVaultsByCurrencyAndValidate(String currencyCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CURRENCY, currencyCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CLIENT_CODE + " with expected status code {0}")
    public static List<Vault> searchVaultsByClientCodeAndValidate(String clientCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CLIENT_CODE, clientCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CLIENT_NAME + " with expected status code {0}")
    public static List<Vault> searchVaultsByClientNameAndValidate(String clientNameOrTitle, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CLIENT_NAME, clientNameOrTitle);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CLIENT_RANK + " with expected status code {0}")
    public static List<Vault> searchVaultsByClientRankAndValidate(String clientRankCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CLIENT_RANK, clientRankCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CLIENT_TYPE + " with expected status code {0}")
    public static List<Vault> searchVaultsByClientTypeAndValidate(String clientTypeCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CLIENT_TYPE, clientTypeCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("GET " + Api.SEARCH_VAULTS_BY_CLIENT_SECTOR + " with expected status code {0}")
    public static List<Vault> searchVaultsByClientSectorAndValidate(String clientSectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SEARCH_VAULTS_BY_CLIENT_SECTOR, clientSectorCode);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }
}
