# Selenium BDD Framework

A production-ready test automation framework using **Java + Selenium + REST Assured + TestNG + Cucumber + Maven**.

---

## 📁 Project Structure

```
selenium-bdd-framework/
├── src/
│   └── test/
│       ├── java/com/framework/
│       │   ├── hooks/
│       │   │   └── Hooks.java              # Before/After scenario hooks
│       │   ├── pages/
│       │   │   ├── BasePage.java           # Base page with common actions
│       │   │   ├── LoginPage.java          # Login page object
│       │   │   └── InventoryPage.java      # Inventory page object
│       │   ├── runners/
│       │   │   ├── TestNGCucumberRunner.java  # Main runner (all tests)
│       │   │   └── SmokeTestRunner.java       # Smoke test runner
│       │   ├── steps/
│       │   │   ├── LoginSteps.java         # UI step definitions
│       │   │   └── ApiSteps.java           # API step definitions
│       │   └── utils/
│       │       ├── ConfigReader.java        # Property file reader
│       │       ├── DriverManager.java       # ThreadLocal WebDriver manager
│       │       ├── RestAssuredUtils.java    # REST Assured helper
│       │       ├── ScenarioContext.java     # Context sharing between steps
│       │       └── SeleniumUtils.java       # Selenium helper methods
│       └── resources/
│           ├── config/
│           │   └── config.properties        # Environment configuration
│           ├── features/
│           │   ├── Login.feature            # UI BDD scenarios
│           │   └── UserAPI.feature          # API BDD scenarios
│           ├── extent.properties            # Extent Reports config
│           └── log4j2.xml                   # Logging configuration
├── testng.xml                               # TestNG suite configuration
└── pom.xml                                  # Maven dependencies
```

---

## ⚙️ Tech Stack

| Component        | Technology              | Version |
|-----------------|-------------------------|---------|
| Language         | Java                    | 11+     |
| Build Tool       | Maven                   | 3.8+    |
| Test Framework   | TestNG                  | 7.9.0   |
| BDD Framework    | Cucumber                | 7.15.0  |
| UI Automation    | Selenium WebDriver      | 4.18.1  |
| API Testing      | REST Assured            | 5.4.0   |
| Browser Manager  | WebDriverManager        | 5.7.0   |
| Reporting        | ExtentReports + Cucumber| 5.1.1   |
| Logging          | Log4j2                  | 2.22.1  |

---

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.8 or higher
- Chrome/Firefox/Edge browser

### Installation
```bash
git clone <repo-url>
cd selenium-bdd-framework
mvn clean install -DskipTests
```

---

## ▶️ Running Tests

### Run all tests
```bash
mvn clean test
```

### Run smoke tests only
```bash
mvn clean test -Dcucumber.filter.tags="@smoke"
```

### Run API tests only
```bash
mvn clean test -Dcucumber.filter.tags="@api"
```

### Run UI tests only
```bash
mvn clean test -Dcucumber.filter.tags="@ui"
```

### Run with different browser
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run in headless mode
```bash
mvn clean test -Dheadless=true
```

### Run against different environment
```bash
mvn clean test -Denv=staging
```

### Run specific feature file
```bash
mvn clean test -Dcucumber.features="src/test/resources/features/Login.feature"
```

### Generate reports after test run
```bash
mvn verify
```

---

## 📊 Reports

After execution, reports are generated in:

| Report Type       | Location                                        |
|------------------|-------------------------------------------------|
| Extent Spark     | `target/ExtentReports/SparkReport.html`         |
| Cucumber HTML    | `target/cucumber-reports/cucumber-html-report.html` |
| Cucumber JSON    | `target/cucumber-reports/cucumber-json-report.json` |
| Maven Cucumber   | `target/cucumber-html-reports/overview-features.html` |
| Screenshots      | `test-output/screenshots/`                      |
| Logs             | `test-output/logs/framework.log`                |

---

## ✍️ Writing Tests

### 1. Create a Feature File
```gherkin
@ui @login
Feature: My Feature

  @smoke
  Scenario: My Test Scenario
    Given some precondition
    When some action is performed
    Then expected result should occur
```

### 2. Create a Page Object
```java
public class MyPage extends BasePage {
    @FindBy(id = "element-id")
    private WebElement myElement;
    
    public void performAction() {
        myElement.click();
    }
}
```

### 3. Create Step Definitions
```java
public class MySteps {
    private MyPage myPage = new MyPage();
    
    @Given("some precondition")
    public void somePrecondition() {
        myPage.navigateToPage(ConfigReader.getBaseUrl());
    }
    
    @When("some action is performed")
    public void someAction() {
        myPage.performAction();
    }
    
    @Then("expected result should occur")
    public void expectedResult() {
        Assert.assertTrue(myPage.isResultDisplayed());
    }
}
```

### 4. Create API Test
```gherkin
@api
Scenario: Get user by ID
  When I send a GET request to "/users/1"
  Then the response status code should be 200
  And the response "data.id" value should be "1"
```

---

## 🏷️ Available Tags

| Tag          | Description                        |
|-------------|------------------------------------|
| `@smoke`    | Smoke/sanity test suite            |
| `@regression` | Full regression suite            |
| `@api`      | API tests (no browser required)    |
| `@ui`       | UI tests (browser required)        |
| `@login`    | Login-related tests                |
| `@positive` | Positive/happy path scenarios      |
| `@negative` | Negative/error path scenarios      |
| `@wip`      | Work in progress (excluded by default) |

---

## 🔧 Configuration

Edit `src/test/resources/config/config.properties`:

```properties
browser=chrome                  # chrome | firefox | edge
headless=false                  # true | false
base.url=https://your-app.com   # UI base URL
api.base.url=https://your-api.com/api  # API base URL
implicit.wait=10                # Seconds
explicit.wait=20                # Seconds
screenshot.on.failure=true      # Capture screenshot on failure
```

---

## 🤝 Contributing

1. Create a feature branch
2. Write feature files first (BDD approach)
3. Implement step definitions
4. Add page objects for UI tests
5. Run tests and ensure they pass
6. Submit a pull request
