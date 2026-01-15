package org.skopintsev.transport;

import static org.apache.http.HttpHeaders.*;
import static org.skopintsev.constants.Constants.*;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Collections;
import java.util.Map;

public class CommonApiReqHelper {

    public static Response postRequest(String bodyReq, String contextReq) {
        return postRequestWithQueryParams(bodyReq, contextReq, Collections.emptyMap());
    }

    public static Response getRequest(String contextReq) {
        return getRequestWithQueryParams(contextReq, Collections.emptyMap());
    }

    public static Response postRequestWithQueryParams(
            String bodyreq,
            String contextReq,
            // String authToken,
            Map<String, Object> params) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here

        if (bodyreq != null) {
            requestSpecification.body(bodyreq);
        }

        requestSpecification.queryParams(params);

        return requestSpecification.post(contextReq);
    }

    public static Response getRequestWithQueryParams(
            String contextReq,
            // String authToken,
            Map<String, Object> params) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here

        requestSpecification.queryParams(params);

        return requestSpecification.get(contextReq);
    }

    private static RequestSpecification prepareRequest() {
        return RestAssured.given()
                .baseUri(BANK_API_URL)
                .config(getRelaxedHttpsValidationSslConfig())
                .contentType(ContentType.JSON)
                .header(ACCEPT, ContentType.ANY)  // Accept: */* -> accepts any response
                .header(HTTP_HEADER_CHARSET, UTF_8)
                //.header(AUTHORIZATION, BEARER + authToken)  todo: add auth token here
                .filter(new AllureRestAssured());  // Allure integration with RestAssured -> attaches API details to Allure reports
    }

    // disables strict SSL certificate validation -> for testing only: bypasses SSL validation
    private static RestAssuredConfig getRelaxedHttpsValidationSslConfig() {
        return RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());
    }



}
