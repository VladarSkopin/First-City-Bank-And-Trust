package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.*;
import org.skopintsev.model.sectors.Sector;
import org.skopintsev.model.sectors.SubSector;
import org.skopintsev.model.vaults.Vault;
import org.skopintsev.model.vaults.VaultOperation;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class PostApiReqHelper {


//    @Step("POST " + Api.CURRENCIES + " with expected status code {1}")
//    public static void saveCurrencyAndValidate(Currency currency, int expectedStatusCode) {
//        Response response = postApiReq(currency, Api.CURRENCIES);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.DISTRICTS + " with expected status code {1}")
//    public static void saveDistrictAndValidate(District district, int expectedStatusCode) {
//        Response response = postApiReq(district, Api.DISTRICTS);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.SOCIAL_RANKS + " with expected status code {1}")
//    public static void saveSocialRankAndValidate(SocialRank socialRank, int expectedStatusCode) {
//        Response response = postApiReq(socialRank, Api.SOCIAL_RANKS);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.CLIENT_TYPES + " with expected status code {1}")
//    public static void saveClientTypeAndValidate(ClientType clientType, int expectedStatusCode) {
//        Response response = postApiReq(clientType, Api.CLIENT_TYPES);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.SECTORS + " with expected status code {1}")
//    public static void saveSectorAndValidate(Sector sector, int expectedStatusCode) {
//        Response response = postApiReq(sector, Api.SECTORS);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.SUB_SECTORS + " with expected status code {1}")
//    public static void saveSubSectorAndValidate(SubSector subSector, int expectedStatusCode) {
//        Response response = postApiReq(subSector, Api.SUB_SECTORS);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.CLIENTS + " with expected status code {1}")
//    public static void saveClientAndValidate(Client client, int expectedStatusCode) {
//        Response response = postApiReq(client, Api.CLIENTS);
//        response.then().statusCode(expectedStatusCode);
//    }

//    @Step("POST " + Api.VAULTS + " with expected status code {1}")
//    public static void saveVaultAndValidate(Vault vault, int expectedStatusCode) {
//        Response response = postApiReq(vault, Api.VAULTS);
//        response.then().statusCode(expectedStatusCode);
//    }

    @Step("POST " + Api.VAULT_OPERATIONS + " with expected status code {1}")
    public static void saveVaultOperationAndValidate(VaultOperation vaultOperation, int expectedStatusCode) {
        Response response = postApiReq(vaultOperation, Api.VAULT_OPERATIONS);
        response.then().statusCode(expectedStatusCode);
    }
}
