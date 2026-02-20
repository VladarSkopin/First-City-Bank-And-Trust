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

    @SneakyThrows
    public static Response getApiRequest(String contextReq) {
        return CommonApiReqHelper.getRequest(contextReq);
    }

    @Step("GET " + Api.CURRENCIES + " with expected status code {0}")
    public static List<Currency> getCurrenciesAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Currency>>() {});
    }

    @Step("GET " + Api.DISTRICTS + " with expected status code {0}")
    public static List<District> getDistrictsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<District>>() {});
    }

    @Step("GET " + Api.SOCIAL_RANKS + " with expected status code {0}")
    public static List<SocialRank> getSocialRanksAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.SOCIAL_RANKS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<SocialRank>>() {});
    }

    @Step("GET " + Api.CLIENT_TYPES + " with expected status code {0}")
    public static List<ClientType> getClientTypesAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.CLIENT_TYPES);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<ClientType>>() {});
    }

    @Step("GET " + Api.SECTORS + " with expected status code {0}")
    public static List<Sector> getSectorsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Sector>>() {});
    }

    @Step("GET " + Api.SUB_SECTORS + " with expected status code {0}")
    public static List<SubSector> getSubSectorsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.SUB_SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<SubSector>>() {});
    }

    @Step("GET " + Api.CLIENTS + " with expected status code {0}")
    public static List<Client> getClientsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.CLIENTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("GET " + Api.VAULTS + " with expected status code {0}")
    public static List<Vault> getVaultsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.VAULTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }
}
