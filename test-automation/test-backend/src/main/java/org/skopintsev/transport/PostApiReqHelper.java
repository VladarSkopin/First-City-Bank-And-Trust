package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.skopintsev.constants.Api;
import org.skopintsev.model.Currency;
import org.skopintsev.model.District;

import static org.skopintsev.constants.Constants.OBJECT_MAPPER;

public class PostApiReqHelper {

    @SneakyThrows
    public static Response postApiReq(Object request, String uri) {
        String requestJson = OBJECT_MAPPER.writeValueAsString(request);
        return postApiReq(requestJson, uri);
    }

    public static Response postApiReq(String bodyReq, String contextReq) {
        return CommonApiReqHelper.postRequest(bodyReq, contextReq);
    }

    @Step("POST " + Api.CURRENCIES + " with expected status code {1}")
    public static void saveCurrencyAndValidate(Currency currency, int expectedStatusCode) {
        Response response = postApiReq(currency, Api.CURRENCIES);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("POST " + Api.DISTRICTS + " with expected status code {1}")
    public static void saveDistrictAndValidate(District district, int expectedStatusCode) {
        Response response = postApiReq(district, Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);
    }

}
