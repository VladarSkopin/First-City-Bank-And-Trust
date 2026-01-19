package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;
import org.skopintsev.model.District;

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

        // TypeRef to handle generic types
        return response.as(new TypeRef<List<Currency>>() {});
    }

    @Step("GET " + Api.DISTRICTS + " with expected status code {0}")
    public static List<District> getDistrictsAndValidate(int expectedStatusCode) {
        Response response = getApiRequest(Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<District>>() {});
    }
}
