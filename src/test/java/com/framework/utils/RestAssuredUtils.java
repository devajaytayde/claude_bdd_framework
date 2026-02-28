package com.framework.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static io.restassured.filter.log.LogDetail.*;


public class RestAssuredUtils {

    private static final Logger logger = LogManager.getLogger(RestAssuredUtils.class);
    private static ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();

    public static void initRequestSpec() {
        RestAssured.baseURI = ConfigReader.getApiBaseUrl();

        String apiKey = ConfigReader.getApiKey();

        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().ifValidationFails();

        // Add API key header if configured
        if (apiKey != null && !apiKey.isEmpty()) {
            spec = spec.header("x-api-key", apiKey);
            logger.info("API key header added to request spec");
        }

        requestSpec.set(spec);
        logger.info("REST Assured initialized with base URI: {}", RestAssured.baseURI);

    }

    public static RequestSpecification getRequestSpec() {
        if (requestSpec.get() == null) {
            initRequestSpec();
        }
        return requestSpec.get();
    }

    public static Response get(String endpoint) {
        logger.info("GET request to: {}", endpoint);
        Response response = getRequestSpec().when().get(endpoint);
        logResponse(response);
        return response;
    }

    public static Response get(String endpoint, Map<String, String> queryParams) {
        logger.info("GET request to: {} with params: {}", endpoint, queryParams);
        Response response = getRequestSpec().queryParams(queryParams).when().get(endpoint);
        logResponse(response);
        return response;
    }

    public static Response post(String endpoint, Object body) {
        logger.info("POST request to: {}", endpoint);
        Response response = getRequestSpec().body(body).when().post(endpoint);
        logResponse(response);
        return response;
    }

    public static Response put(String endpoint, Object body) {
        logger.info("PUT request to: {}", endpoint);
        Response response = getRequestSpec().body(body).when().put(endpoint);
        logResponse(response);
        return response;
    }

    public static Response patch(String endpoint, Object body) {
        logger.info("PATCH request to: {}", endpoint);
        Response response = getRequestSpec().body(body).when().patch(endpoint);
        logResponse(response);
        return response;
    }

    public static Response delete(String endpoint) {
        logger.info("DELETE request to: {}", endpoint);
        Response response = getRequestSpec().when().delete(endpoint);
        logResponse(response);
        return response;
    }

    public static Response getWithAuth(String endpoint, String token) {
        logger.info("GET request with auth to: {}", endpoint);
        Response response = getRequestSpec()
                .header("Authorization", "Bearer " + token)
                .when().get(endpoint);
        logResponse(response);
        return response;
    }

    private static void logResponse(Response response) {
        logger.info("Response Status: {} | Time: {}ms",
                response.getStatusCode(), response.getTime());
        logger.debug("Response Body: {}", response.getBody().asString());
    }

    public static void validateStatusCode(Response response, int expectedCode) {
        int actualCode = response.getStatusCode();
        if (actualCode != expectedCode) {
            throw new AssertionError(
                    String.format("Expected status %d but got %d. Body: %s",
                            expectedCode, actualCode, response.getBody().asString()));
        }
        logger.info("Status code {} validated successfully", actualCode);
    }

    public static <T> T getJsonPath(Response response, String path) {
        return response.jsonPath().get(path);
    }
}
