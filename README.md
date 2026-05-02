# 🧪 TutorialsNinja Automation Framework
 
A robust end-to-end automation framework built using **Selenium WebDriver**, **Java**, and **TestNG** for the [TutorialsNinja](https://tutorialsninja.com/demo/) demo e-commerce application. This project demonstrates real-world QA automation practices including scalable framework design, reusable components, CI/CD integration, and comprehensive test coverage across critical user flows.
 
---
 
## 🚀 Project Overview
 
This framework is designed using the **Page Object Model (POM)** to ensure maintainability, readability, and reusability of test code. It automates key e-commerce functionalities such as user registration, login, product search, wishlist management, cart operations, product comparison, and checkout flow.
 
The framework integrates **Extent Reports** for detailed HTML execution reports, supports **parallel test execution** via TestNG, runs inside **Docker** for environment consistency, and is wired to a **Jenkins CI/CD pipeline** for automated build and test execution.
 
---
 
## 🧱 Framework Architecture
 
- **Page Object Model (POM)** design pattern
- Separate layers for Test Cases, Page Objects, Utilities, and Base setup
- Centralized, thread-safe `WebDriver` initialization via `DriverFactory` using `ThreadLocal`
- `HeaderComponent` for shared navigation actions (login, logout, cart, wishlist)
- `BasePage` with reusable explicit wait wrappers for all Selenium interactions
- Configurable via `config.properties` — browser, URL, timeouts, headless mode
- Listener-based Extent Report integration with automatic screenshot capture on failure
---
 
## 🗂️ Project Structure
 
```
src
├── main/java
│   ├── base
│   │   ├── BasePage.java
│   │   ├── BaseTest.java
│   │   └── DriverFactory.java
│   ├── pages
│   │   ├── HeaderComponent.java
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── RegisterPage.java
│   │   ├── SearchPage.java
│   │   ├── ProductPage.java
│   │   ├── CartPage.java
│   │   ├── WishlistPage.java
│   │   ├── CheckoutPage.java
│   │   ├── ComparePage.java
│   │   └── ...
│   ├── listeners
│   │   └── TestListener.java
│   └── utils
│       ├── ExtentManager.java
│       ├── ScreenshotUtil.java
│       └── TestData.java
└── test
    ├── java/tests
    │   ├── registration
    │   ├── login
    │   ├── search
    │   ├── product
    │   ├── wishlist
    │   ├── cart
    │   ├── checkout
    │   └── compare
    └── resources
        └── config
            └── config.properties
```
 
---
 
## 🔄 Test Coverage
 
| Module | Test Cases | Key Scenarios |
|---|---|---|
| Registration | TC001–TC004 | Mandatory fields, duplicate email, privacy policy, password mismatch |
| Login | TC005–TC006 | Valid/invalid credentials (data-driven), forgot password navigation |
| Search | TC007–TC012 | Keyword, partial match, category filter, description search, empty search |
| Product | TC013–TC016 | Product details, image thumbnails, review submission, related products |
| Wishlist | TC017–TC022 | Add/remove, multiple products, persistence, guest redirect, wishlist→cart |
| Cart | TC023–TC030 | Add/update/remove, multiple items, quantity update, persistence (refresh + login) |
| Checkout | TC031–TC033 | Full checkout flow, empty cart validation, terms & conditions enforcement |
| Compare | TC034–TC036 | Add/remove single and multiple products to comparison |
 
**Total: 36 automated test cases**
 
---
 
## ⚡ Key Capabilities
 
- **Thread-safe parallel execution** — `ThreadLocal<WebDriver>` in `DriverFactory` ensures each thread gets an isolated browser instance
- **Parallel TestNG suite** — `testng-parallel.xml` runs 4 test groups simultaneously with safe thread grouping
- **Cross-browser support** — Chrome, Firefox, and Edge configurable via `config.properties` or `-Dbrowser` flag
- **Headless execution** — configurable via property or `-Dheadless=true` for CI/Docker runs
- **Data-driven testing** — `@DataProvider` used for login scenario coverage
- **CI/CD pipeline** — Jenkinsfile with parameterized build, Docker and direct Maven modes, artifact archiving
- **Dockerized execution** — Dockerfile with Chrome + Maven + JDK 21 for fully containerized test runs
- **Extent Reports** — HTML reports with step-level logging and failure screenshots via `TestListener`
- **Log4j2 logging** — structured logging across all framework layers
---
 
## 🛠️ Tech Stack
 
| Tool | Purpose |
|---|---|
| Java 21 | Primary language |
| Selenium WebDriver 4 | Browser automation |
| TestNG | Test framework & parallel execution |
| Maven | Build and dependency management |
| WebDriverManager | Automatic driver binary management |
| Extent Reports 5 | HTML test execution reporting |
| Log4j2 | Framework-level logging |
| Jenkins | CI/CD pipeline |
| Docker | Containerized test execution |
| Git & GitHub | Version control |
 
---
 
## ⚙️ How to Run
 
### Prerequisites
- Java 21+
- Maven 3.9+
- Google Chrome (for local runs)
- Docker (for containerized runs)
### Run locally
```bash
# Clone the repo
git clone https://github.com/ADII0412/tutorialsninja-automation-framework.git
cd tutorialsninja-automation-framework
 
# Run with default config (Chrome, headed)
mvn clean test
 
# Run headless
mvn clean test -Dheadless=true
 
# Run on Firefox
mvn clean test -Dbrowser=firefox
 
# Run parallel suite
mvn clean test -DsuiteXmlFile=testng-parallel.xml
```
 
### Run with Docker
```bash
# Build the image
docker build -t tutorialsninja-automation .
 
# Run tests (headless Chrome by default)
docker run tutorialsninja-automation
 
# Override browser or URL
docker run -e BROWSER=firefox tutorialsninja-automation
```
 
### Run via Jenkins
1. Create a new Pipeline job in Jenkins
2. Point it to this repository
3. Jenkins will detect the `Jenkinsfile` automatically
4. Use the `RUN_IN_DOCKER`, `BROWSER`, and `BASE_URL` parameters to configure the run
5. Extent Reports and screenshots are archived as build artifacts after each run
---
 
## 📊 Reporting
 
After each test run, an **Extent HTML report** is generated in the `reports/` directory containing:
- Test-level pass/fail status
- Step-by-step execution log
- Failure screenshots embedded in the report
- Thread-level reporting for parallel runs
---
 
## 📌 Author
 
**Aditya Singh** — QA Automation Engineer  
[GitHub](https://github.com/ADII0412)
