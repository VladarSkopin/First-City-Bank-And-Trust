package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.sectors.Sector;
import org.skopintsev.model.sectors.SubSector;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class SectorsApiClient {

    @Step("GET " + Api.SECTORS + " with expected status code {0}")
    public static List<Sector> getSectorsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<Sector>>() {});
    }

    @Step("POST " + Api.SECTORS + " with expected status code {1}")
    public static void saveSectorAndValidate(Sector sector, int expectedStatusCode) {
        Response response = postApiReq(sector, Api.SECTORS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.SECTORS + " with expected status code {1}")
    public static void deleteSectorAndValidate(String sectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.SECTORS, sectorCode);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("GET " + Api.SUB_SECTORS + " with expected status code {0}")
    public static List<SubSector> getSubSectorsAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SUB_SECTORS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<SubSector>>() {});
    }

    @Step("POST " + Api.SUB_SECTORS + " with expected status code {1}")
    public static void saveSubSectorAndValidate(SubSector subSector, int expectedStatusCode) {
        Response response = postApiReq(subSector, Api.SUB_SECTORS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.SUB_SECTORS + " with expected status code {1}")
    public static void deleteSubSectorAndValidate(String subSectorCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.SUB_SECTORS, subSectorCode);
        response.then().statusCode(expectedStatusCode);
    }
}
