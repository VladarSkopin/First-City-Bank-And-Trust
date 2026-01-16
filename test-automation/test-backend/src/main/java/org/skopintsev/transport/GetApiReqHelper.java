package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;

import java.util.List;


public class GetApiReqHelper {

    @SneakyThrows
    public static Response getApiReq(String contextReq) {
        return CommonApiReqHelper.getRequest(contextReq);
    }

    @Step("GET " + Api.CURRENCIES + " with expected status code {0}")
    public static List<Currency> getCurrenciesAndValidate(int expectedStatusCode) {
        Response response = getApiReq(Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);

        // Use TypeRef to handle generic types
        return response.as(new TypeRef<List<Currency>>() {});
    }
}
