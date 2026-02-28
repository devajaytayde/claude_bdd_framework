package com.framework.steps;

import com.framework.utils.RestAssuredUtils;
import com.framework.utils.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        Response response = RestAssuredUtils.get(endpoint);
        ScenarioContext.setApiResponse(response);
    }

    @When("I send a GET request to {string} with parameter {string} as {string}")
    public void iSendAGetRequestWithParam(String endpoint, String paramName, String paramValue) {
        Map<String, String> params = new HashMap<>();
        params.put(paramName, paramValue);
        Response response = RestAssuredUtils.get(endpoint, params);
        ScenarioContext.setApiResponse(response);
    }

    @When("I send a POST request to {string} with the user data")
    public void iSendAPostRequest(String endpoint) {
        Map<String, String> userData = ScenarioContext.get("USER_DATA");
        Response response = RestAssuredUtils.post(endpoint, userData);
        ScenarioContext.setApiResponse(response);
    }

    @When("I send a PUT request to {string} with the user data")
    public void iSendAPutRequest(String endpoint) {
        Map<String, String> userData = ScenarioContext.get("USER_DATA");
        Response response = RestAssuredUtils.put(endpoint, userData);
        ScenarioContext.setApiResponse(response);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADeleteRequest(String endpoint) {
        Response response = RestAssuredUtils.delete(endpoint);
        ScenarioContext.setApiResponse(response);
    }

    @Given("I have the following user data:")
    public void iHaveTheFollowingUserData(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        ScenarioContext.set("USER_DATA", userData);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = ScenarioContext.getApiResponse();
        Assert.assertNotNull(response, "No API response found in scenario context");
        RestAssuredUtils.validateStatusCode(response, expectedStatusCode);
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String fieldName) {
        Response response = ScenarioContext.getApiResponse();
        Assert.assertNotNull(response.jsonPath().get(fieldName),
                String.format("Response does not contain field: %s", fieldName));
    }

    @Then("the response {string} value should be {string}")
    public void theResponseValueShouldBe(String jsonPath, String expectedValue) {
        Response response = ScenarioContext.getApiResponse();
        Object actualValue = RestAssuredUtils.getJsonPath(response, jsonPath);
        Assert.assertNotNull(actualValue,
                String.format("JSON path '%s' not found in response", jsonPath));
        Assert.assertEquals(actualValue.toString(), expectedValue,
                String.format("Value at '%s' mismatch", jsonPath));
    }

    @Then("the response {string} should not be empty")
    public void theResponseFieldShouldNotBeEmpty(String jsonPath) {
        Response response = ScenarioContext.getApiResponse();
        Object value = RestAssuredUtils.getJsonPath(response, jsonPath);
        Assert.assertNotNull(value,
                String.format("Field '%s' is null", jsonPath));
        Assert.assertFalse(value.toString().isEmpty(),
                String.format("Field '%s' is empty", jsonPath));
    }
}
