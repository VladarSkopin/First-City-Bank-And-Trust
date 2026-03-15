package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;

public class DeleteApiReqHelper {

//    @Step("DELETE " + Api.CURRENCIES + " with expected status code {1}")
//    public static void deleteCurrencyAndValidate(String currencyCode, int expectedStatusCode) {
//        Response response = CommonApiReqHelper.deleteRequest(Api.CURRENCIES, currencyCode);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("DELETE " + Api.DISTRICTS + " with expected status code {1}")
//    public static void deleteDistrictAndValidate(String districtCode, int expectedStatusCode) {
//        Response response = CommonApiReqHelper.deleteRequest(Api.DISTRICTS, districtCode);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("DELETE " + Api.SOCIAL_RANKS + " with expected status code {1}")
//    public static void deleteSocialRankAndValidate(String rankCode, int expectedStatusCode) {
//        Response response = CommonApiReqHelper.deleteRequest(Api.SOCIAL_RANKS, rankCode);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("DELETE " + Api.CLIENT_TYPES + " with expected status code {1}")
//    public static void deleteClientTypeAndValidate(String clientTypeCode, int expectedStatusCode) {
//        Response response = CommonApiReqHelper.deleteRequest(Api.CLIENT_TYPES, clientTypeCode);
//        response.then().statusCode(expectedStatusCode);
//    }

    @Step("DELETE " + Api.SECTORS + " with expected status code {1}")
    public static void deleteSectorAndValidate(String sectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.SECTORS, sectorCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.SUB_SECTORS + " with expected status code {1}")
    public static void deleteSubSectorAndValidate(String subSectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.SUB_SECTORS, subSectorCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.CLIENTS + " with expected status code {1}")
    public static void deleteClientAndValidate(String clientCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.CLIENTS, clientCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.VAULTS + " with expected status code {1}")
    public static void deleteVaultAndValidate(String vaultCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.VAULTS, vaultCode);
        response.then().statusCode(expectedStatusCode);
    }
}
