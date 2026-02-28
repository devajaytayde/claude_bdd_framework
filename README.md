Let me check the relevant skill files and then build you a complete working BDD framework project.Now let me build the complete framework project:Now let's package it all into a ZIP file:Here's your complete working BDD framework! Here's what's included:

## 📦 Framework Components

**Core Utilities**
- `DriverManager` — ThreadLocal WebDriver (supports Chrome, Firefox, Edge + headless)
- `ConfigReader` — Reads `config.properties`, supports system property overrides (for CI/CD)
- `SeleniumUtils` — Explicit waits, click, type, screenshot, JS executor helpers
- `RestAssuredUtils` — GET/POST/PUT/PATCH/DELETE with logging, auth support
- `ScenarioContext` — Thread-safe context for sharing data between steps

**Page Objects (POM)**
- `BasePage` → `LoginPage`, `InventoryPage` — uses `@FindBy` + `PageFactory`

**BDD Layer**
- `Login.feature` — UI scenarios with Scenario Outline + Examples table
- `UserAPI.feature` — Full CRUD API scenarios (uses reqres.in as demo)
- `LoginSteps.java` + `ApiSteps.java` — step definitions with TestNG assertions
- `Hooks.java` — `@Before`/`@After` per scenario, auto-screenshot on failure

**Runners & Reports**
- `TestNGCucumberRunner` — runs all tests
- `SmokeTestRunner` — runs `@smoke` tagged tests only
- `testng.xml` — TestNG suite config
- Extent Reports, Cucumber HTML/JSON reports auto-generated

## 🚀 Quick Start
```bash
unzip selenium-bdd-framework.zip && cd selenium-bdd-framework
mvn clean test                          # Run all tests
mvn clean test -Dbrowser=firefox        # Firefox
mvn clean test -Dcucumber.filter.tags="@api"   # API only
mvn clean test -Dheadless=true          # Headless mode
```


