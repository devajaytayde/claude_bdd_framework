package com.framework.hooks;

import com.framework.utils.ConfigReader;
import com.framework.utils.DriverManager;
import com.framework.utils.RestAssuredUtils;
import com.framework.utils.ScenarioContext;
import com.framework.utils.SeleniumUtils;
import io.cucumber.java.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========== Starting Scenario: {} ==========", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());

        // Initialize REST Assured for API scenarios
        if (scenario.getSourceTagNames().contains("@api")) {
            logger.info("Initializing REST Assured for API scenario");
            RestAssuredUtils.initRequestSpec();
        }

        // Initialize browser for UI scenarios
        if (!scenario.getSourceTagNames().contains("@api")) {
            logger.info("Initializing WebDriver for UI scenario");
            DriverManager.initDriver();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("========== Ending Scenario: {} | Status: {} ==========",
                scenario.getName(), scenario.getStatus());

        // Take screenshot on failure for UI tests
        if (scenario.isFailed() && ConfigReader.isScreenshotOnFailure()) {
            try {
                byte[] screenshot = SeleniumUtils.captureScreenshotAsBytes();
                if (screenshot != null) {
                    scenario.attach(screenshot, "image/png", "Failure Screenshot");
                    logger.info("Screenshot attached to failed scenario");
                }
            } catch (Exception e) {
                logger.warn("Could not capture screenshot: {}", e.getMessage());
            }
        }

        // Clear scenario context
        ScenarioContext.clear();

        // Quit driver for UI tests
        if (!scenario.getSourceTagNames().contains("@api")) {
            DriverManager.quitDriver();
        }
    }

    @BeforeStep
    public void beforeStep() {
        // Add any pre-step logic here if needed
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Capture screenshot after each step (optional, uncomment if needed)
        // if (!scenario.getSourceTagNames().contains("@api")) {
        //     byte[] screenshot = SeleniumUtils.captureScreenshotAsBytes();
        //     scenario.attach(screenshot, "image/png", "Step Screenshot");
        // }
    }
}
