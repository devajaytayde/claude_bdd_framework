package com.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from: {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("Failed to load config file: {}", CONFIG_FILE, e);
            throw new RuntimeException("Cannot load config.properties file", e);
        }
    }

    public static String getProperty(String key) {
        // System property overrides config file (useful for CI/CD)
        String value = System.getProperty(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in config file", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public static String getApiKey() {
        return getProperty("api.key", "");
    }

}
