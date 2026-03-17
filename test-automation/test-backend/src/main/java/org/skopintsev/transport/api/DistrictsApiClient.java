package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.District;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class DistrictsApiClient {

    @Step("GET " + Api.DISTRICTS + " with expected status code {0}")
    public static List<District> getDistrictsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<District>>() {});
    }

    @Step("POST " + Api.DISTRICTS + " with expected status code {1}")
    public static void saveDistrictAndValidate(District district, int expectedStatusCode) {
        Response response = postApiReq(district, Api.DISTRICTS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.DISTRICTS + " with expected status code {1}")
    public static void deleteDistrictAndValidate(String districtCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.DISTRICTS, districtCode);
        response.then().statusCode(expectedStatusCode);
    }

}
