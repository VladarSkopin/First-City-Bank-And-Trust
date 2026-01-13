package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;

import java.util.List;

import static org.skopintsev.constants.Constants.BANK_API_URL;
import static org.skopintsev.transport.CommonApiReqHelper.getRequest;


public class GetApiReqHelper {

    @SneakyThrows
    public static Response getApiReq(String contextReq) {
        return getRequest(contextReq, BANK_API_URL);
    }

    public static Response getCurrencies() {
        return getApiReq(Api.CURRENCIES);
    }

    @Step("GET " + Api.CURRENCIES + " by expected status code {0}")
    public static List<Currency> getCurrenciesAndValidate(int expectedStatusCode) {
        Response response = getCurrencies();
        response.then().statusCode(expectedStatusCode);

        // Use TypeRef to handle generic types
        return response.as(new TypeRef<List<Currency>>() {});
    }
}
