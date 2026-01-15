package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;

public class DeleteApiReqHelper {

    public static Response deleteCurrency(String currencyCode) {
        return CommonApiReqHelper.deleteRequest(Api.CURRENCIES, currencyCode);
    }

    @Step("DELETE " + Api.CURRENCIES + " by expected status code {1}")
    public static void deleteCurrencyAndValidate(String currencyCode, int expectedStatusCode) {
        Response response = deleteCurrency(currencyCode);
        response.then().statusCode(expectedStatusCode);
    }

}
