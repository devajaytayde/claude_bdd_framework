package com.framework.pages;

import com.framework.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    // Locators using @FindBy
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // By locators (alternative approach)
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By PAGE_LOGO = By.className("login_logo");

    public LoginPage() {
        super();
    }

    public void navigateToLoginPage() {
        navigateToPage(com.framework.utils.ConfigReader.getBaseUrl());
        logger.info("Navigated to Login Page");
    }

    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        logger.info("Clicking login button");
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getErrorMessage() {
        return SeleniumUtils.getText(ERROR_MESSAGE);
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(ERROR_MESSAGE);
    }

    public boolean isLoginPageDisplayed() {
        return isElementVisible(PAGE_LOGO);
    }
}
