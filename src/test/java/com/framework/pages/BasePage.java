package com.framework.pages;

import com.framework.utils.DriverManager;
import com.framework.utils.SeleniumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void navigateToPage(String url) {
        SeleniumUtils.navigateTo(url);
        logger.info("Navigated to: {}", url);
    }

    public String getPageTitle() {
        return SeleniumUtils.getPageTitle();
    }

    public String getCurrentUrl() {
        return SeleniumUtils.getCurrentUrl();
    }

    public boolean isElementVisible(By locator) {
        return SeleniumUtils.isDisplayed(locator);
    }
}
