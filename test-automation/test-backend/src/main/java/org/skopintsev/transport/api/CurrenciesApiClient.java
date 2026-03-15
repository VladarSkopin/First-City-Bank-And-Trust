package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class CurrenciesApiClient {

    @Step("GET " + Api.CURRENCIES + " with expected status code {0}")
    public static List<Currency> getCurrenciesAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Currency>>() {});
    }

    @Step("POST " + Api.CURRENCIES + " with expected status code {1}")
    public static void saveCurrencyAndValidate(Currency currency, int expectedStatusCode) {
        Response response = postApiReq(currency, Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.CURRENCIES + " with expected status code {1}")
    public static void deleteCurrencyAndValidate(String currencyCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.CURRENCIES, currencyCode);
        response.then().statusCode(expectedStatusCode);
    }

}
