package com.framework.steps;

import com.framework.pages.InventoryPage;
import com.framework.pages.LoginPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Login page is not displayed");
    }

    @When("the user enters username {string} and password {string}")
    public void theUserEntersUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("the user should be redirected to the inventory page")
    public void theUserShouldBeRedirectedToTheInventoryPage() {
        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(),
                "User was not redirected to inventory page. Current URL: "
                        + inventoryPage.getCurrentUrl());
    }

    @Then("the inventory page title should be {string}")
    public void theInventoryPageTitleShouldBe(String expectedTitle) {
        String actualTitle = inventoryPage.getInventoryPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
                "Inventory page title mismatch");
    }

    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Error message was not displayed");
    }

    @Then("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedMessage) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                String.format("Error message '%s' does not contain '%s'",
                        actualMessage, expectedMessage));
    }
}
