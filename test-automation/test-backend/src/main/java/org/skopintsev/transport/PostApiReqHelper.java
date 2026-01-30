package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.*;

import static org.skopintsev.constants.Constants.OBJECT_MAPPER;

public class PostApiReqHelper {

    @SneakyThrows
    public static Response postApiReq(Object request, String uri) {
        String requestJson = OBJECT_MAPPER.writeValueAsString(request);
        return postApiReq(requestJson, uri);
    }

    public static Response postApiReq(String bodyReq, String contextReq) {
        return CommonApiReqHelper.postRequest(bodyReq, contextReq);
    }

    @Step("POST " + Api.CURRENCIES + " with expected status code {1}")
    public static void saveCurrencyAndValidate(Currency currency, int expectedStatusCode) {
        Response response = postApiReq(currency, Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.DISTRICTS + " with expected status code {1}")
    public static void saveDistrictAndValidate(District district, int expectedStatusCode) {
        Response response = postApiReq(district, Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.SOCIAL_RANKS + " with expected status code {1}")
    public static void saveSocialRankAndValidate(SocialRank socialRank, int expectedStatusCode) {
        Response response = postApiReq(socialRank, Api.SOCIAL_RANKS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.CLIENT_TYPES + " with expected status code {1}")
    public static void saveClientTypeAndValidate(ClientType clientType, int expectedStatusCode) {
        Response response = postApiReq(clientType, Api.CLIENT_TYPES);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.SECTORS + " with expected status code {1}")
    public static void saveSectorAndValidate(Sector sector, int expectedStatusCode) {
        Response response = postApiReq(sector, Api.SECTORS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.SUB_SECTORS + " with expected status code {1}")
    public static void saveSubSectorAndValidate(SubSector subSector, int expectedStatusCode) {
        Response response = postApiReq(subSector, Api.SUB_SECTORS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.CLIENTS + " with expected status code {1}")
    public static void saveClientAndValidate(Client client, int expectedStatusCode) {
        Response response = postApiReq(client, Api.CLIENTS);
        response.then().statusCode(expectedStatusCode);
    }

}
