package org.skopintsev.transport.api;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.skopintsev.constants.Api;
import org.skopintsev.model.SocialRank;
import org.skopintsev.transport.CommonApiReqHelper;

import java.util.List;

import static org.skopintsev.transport.CommonApiReqHelper.postApiReq;

public class SocialRanksApiClient {

    @Step("GET " + Api.SOCIAL_RANKS + " with expected status code {0}")
    public static List<SocialRank> getSocialRanksAndValidate(int expectedStatusCode) {
        Response response = CommonApiReqHelper.getRequest(Api.SOCIAL_RANKS);
        response.then().statusCode(expectedStatusCode);

        return response.as(new TypeRef<List<SocialRank>>() {});
    }

    @Step("POST " + Api.SOCIAL_RANKS + " with expected status code {1}")
    public static void saveSocialRankAndValidate(SocialRank socialRank, int expectedStatusCode) {
        Response response = postApiReq(socialRank, Api.SOCIAL_RANKS);
        response.then().statusCode(expectedStatusCode);
    }

    @Step("DELETE " + Api.SOCIAL_RANKS + " with expected status code {1}")
    public static void deleteSocialRankAndValidate(String rankCode, int expectedStatusCode) {
        Response response = CommonApiReqHelper.deleteRequest(Api.SOCIAL_RANKS, rankCode);
        response.then().statusCode(expectedStatusCode);
    }

}
