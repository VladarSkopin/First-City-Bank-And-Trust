package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.vaults.Vault;
import org.skopintsev.model.vaults.VaultOperation;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;


public class VaultsApiClient {

    @Step("GET " + Api.VAULTS + " with expected status code {0}")
    public static List<Vault> getVaultsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.VAULTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Vault>>() {});
    }

    @Step("POST " + Api.VAULTS + " with expected status code {1}")
    public static void saveVaultAndValidate(Vault vault, int expectedStatusCode) {
        Response response = CommonApiReqHelper.postApiReq(vault, Api.VAULTS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.VAULTS + " with expected status code {1}")
    public static void deleteVaultAndValidate(String vaultCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.VAULTS, vaultCode);
        response.then().statusCode(expectedStatusCode);
    }

}
