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
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CommonApiReqHelper {

    @SneakyThrows
    public static Response postApiReq(Object request, String endpoint) {
        String requestJson = OBJECT_MAPPER.writeValueAsString(request);
        return postRequest(requestJson, endpoint);
    }

    public static Response postRequest(String bodyReq, String contextReq) {
        return postRequestWithQueryParams(bodyReq, contextReq, Collections.emptyMap());
    }

    public static Response getRequest(String contextReq) {
        return getRequestWithQueryParams(contextReq, Collections.emptyMap());
    }

    public static Response getRequest(String contextReq, String pathParam) {
        String completePath = contextReq + "/" + pathParam;
        return getRequestWithPathParams(completePath);
    }

    public static Response deleteRequest(String contextReq, String pathParam) {
        String completePath = contextReq + "/" + pathParam;
        return deleteRequestWithPathParams(completePath);
    }

    @SneakyThrows
    public static Response deleteRequest(Object request, String endpoint) {
        String requestJson = OBJECT_MAPPER.writeValueAsString(request);
        return deleteRequestWithQueryParams(requestJson, endpoint, Collections.emptyMap());
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

    @SneakyThrows
    public static Response getRequestWithQueryParams(
            String endpoint,
            // String authToken,
            String... queryParams) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here
        requestSpecification.queryParams(buildQueryParams(queryParams));

        return requestSpecification.get(endpoint);
    }

    public static Response getRequestWithPathParams(
            // String authToken,
            String completePath
    ) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here

        return requestSpecification.get(completePath);
    }

    public static Response deleteRequestWithPathParams(
            // String authToken,
            String completePath
            ) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here

        return requestSpecification.delete(completePath);
    }

    public static Response deleteRequestWithQueryParams(
            String bodyreq,
            String contextReq,
            // String authToken,
            Map<String, Object> params) {
        RequestSpecification requestSpecification = prepareRequest();  // todo: add auth token here

        if (bodyreq != null) {
            requestSpecification.body(bodyreq);
        }

        requestSpecification.queryParams(params);

        return requestSpecification.delete(contextReq);
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

    private static Map<String, String> buildQueryParams(String... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Query params must be provided as key-value pairs");
        }

        Map<String, String> queryParams = new HashMap<>();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i];
            String value = params[i + 1];
            if (value != null) {
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }
}
