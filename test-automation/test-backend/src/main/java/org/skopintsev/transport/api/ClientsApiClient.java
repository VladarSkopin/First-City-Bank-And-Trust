package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Client;
import org.skopintsev.model.ClientType;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class ClientsApiClient {

    @Step("GET " + Api.CLIENT_TYPES + " with expected status code {0}")
    public static List<ClientType> getClientTypesAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.CLIENT_TYPES);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<ClientType>>() {});
    }

    @Step("POST " + Api.CLIENT_TYPES + " with expected status code {1}")
    public static void saveClientTypeAndValidate(ClientType clientType, int expectedStatusCode) {
        Response response = postApiReq(clientType, Api.CLIENT_TYPES);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.CLIENT_TYPES + " with expected status code {1}")
    public static void deleteClientTypeAndValidate(String clientTypeCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.CLIENT_TYPES, clientTypeCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("GET " + Api.CLIENTS + " with expected status code {0}")
    public static List<Client> getClientsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.CLIENTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Client>>() {});
    }

    @Step("POST " + Api.CLIENTS + " with expected status code {1}")
    public static void saveClientAndValidate(Client client, int expectedStatusCode) {
        Response response = postApiReq(client, Api.CLIENTS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.CLIENTS + " with expected status code {1}")
    public static void deleteClientAndValidate(String clientCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.CLIENTS, clientCode);
        response.then().statusCode(expectedStatusCode);
    }

}
