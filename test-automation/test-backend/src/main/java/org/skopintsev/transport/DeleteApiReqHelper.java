package org.skopintsev.transport;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;

public class DeleteApiReqHelper {

    public static Response deleteApiRequest(String endpoint, String currencyCode) {
        return CommonApiReqHelper.deleteRequest(endpoint, currencyCode);
    }

    @Step("DELETE " + Api.CURRENCIES + " with expected status code {1}")
    public static void deleteCurrencyAndValidate(String currencyCode, int expectedStatusCode) {
        Response response = deleteApiRequest(Api.CURRENCIES, currencyCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.DISTRICTS + " with expected status code {1}")
    public static void deleteDistrictAndValidate(String districtCode, int expectedStatusCode) {
        Response response = deleteApiRequest(Api.DISTRICTS, districtCode);
        response.then().statusCode(expectedStatusCode);
    }

}
