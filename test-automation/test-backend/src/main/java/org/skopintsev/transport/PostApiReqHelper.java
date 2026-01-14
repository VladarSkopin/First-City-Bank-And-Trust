package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;

import static org.skopintsev.constants.Constants.BANK_API_URL;
import static org.skopintsev.constants.Constants.OBJECT_MAPPER;
import static org.skopintsev.transport.CommonApiReqHelper.postRequest;

public class PostApiReqHelper {

    @SneakyThrows
    public static Response postApiReq(Object request, String uri) {
        String requestJson = OBJECT_MAPPER.writeValueAsString(request);
        return postApiReq(requestJson, uri);
    }

    public static Response postApiReq(String bodyReq, String contextReq) {
        return postRequest(bodyReq, contextReq, BANK_API_URL);
    }

    public static Response saveCurrency(Currency currency) {
        return postApiReq(currency, Api.CURRENCIES);
    }

    @Step("POST " + Api.CURRENCIES + " by expected status code {1}")
    public static void saveCurrencyAndValidate(Currency currency, int expectedStatusCode) {
        Response response = saveCurrency(currency);
        response.then().statusCode(expectedStatusCode);
    }

}
