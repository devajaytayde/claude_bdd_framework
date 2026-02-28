package com.framework.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class SeleniumUtils {

    private static final Logger logger = LogManager.getLogger(SeleniumUtils.class);

    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(),
                Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    public static WebElement waitForVisibility(By locator) {
        logger.debug("Waiting for element visibility: {}", locator);
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(By locator) {
        logger.debug("Waiting for element clickability: {}", locator);
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void click(By locator) {
        logger.info("Clicking on element: {}", locator);
        waitForClickability(locator).click();
    }

    public static void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public static String getText(By locator) {
        String text = waitForVisibility(locator).getText();
        logger.debug("Got text '{}' from element: {}", text, locator);
        return text;
    }

    public static boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public static void selectByVisibleText(By locator, String text) {
        logger.info("Selecting '{}' from dropdown: {}", text, locator);
        new Select(waitForVisibility(locator)).selectByVisibleText(text);
    }

    public static void scrollToElement(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void jsClick(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].click();", element);
    }

    public static String captureScreenshot(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = ConfigReader.getProperty("screenshot.path", "test-output/screenshots")
                + "/" + testName + "_" + timestamp + ".png";
        try {
            File srcFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotPath);
            FileUtils.copyFile(srcFile, destFile);
            logger.info("Screenshot saved: {}", screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }

    public static byte[] captureScreenshotAsBytes() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        DriverManager.getDriver().get(url);
    }

    public static String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public static String getCurrentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }
}
