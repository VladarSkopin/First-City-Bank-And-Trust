package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.vaults.vault_pools.CreateVaultPoolRequest;
import org.skopintsev.model.vaults.vault_pools.DeleteVaultPoolRequest;
import org.skopintsev.model.vaults.vault_pools.VaultPool;
import org.skopintsev.model.vaults.vault_pools.VaultPoolResponse;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

public class VaultPoolsApiClient {

    @Step("GET " + Api.VAULT_POOLS + " with expected status code {0}")
    public static List<VaultPoolResponse> getVaultPoolsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.VAULT_POOLS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<VaultPoolResponse>>() {});
    }

    @Step("POST " + Api.VAULT_POOLS + " with expected status code {1}")
    public static VaultPool saveVaultPoolAndValidate(CreateVaultPoolRequest createVaultPoolRequest, int expectedStatusCode) {
        Response response = CommonApiReqHelper.postApiReq(createVaultPoolRequest, Api.VAULT_POOLS);
        response.then().statusCode(expectedStatusCode);

        return response.as(VaultPool.class);
    }

    @Step("DELETE " + Api.VAULT_POOLS + " with expected status code {1}")
    public static void deleteVaultPoolAndValidate(DeleteVaultPoolRequest deleteVaultPoolRequest, int expectedStatusCode) {
        String vaultPoolName = deleteVaultPoolRequest.getVaultPoolName();
        // todo: create new API method to execute DELETE with a JSON body
        Response response = CommonApiReqHelper.deleteRequest(Api.VAULT_POOLS, vaultPoolName);
        response.then().statusCode(expectedStatusCode);
    }
}
