package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult; // Added for AfterMethod context
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method; // Added for getting test name
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    protected Properties prop;
    protected final Logger logger = LogManager.getLogger(this.getClass());

    public Properties initializeProperties() {
        prop = new Properties();
        String env = System.getProperty("env", "default").trim().toLowerCase();
        String configFileName = switch (env) {
            case "staging" -> "config-staging.properties";
            case "prod" -> "config-prod.properties";
            case "default" -> "config.properties";
            default -> throw new IllegalArgumentException("Unsupported environment: " + env);
        };

        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config/" + configFileName
        )) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + configFileName, e);
        }

        if (System.getProperty("browser") != null)
            prop.setProperty("browser", System.getProperty("browser"));
        if (System.getProperty("headless") != null)
            prop.setProperty("headless", System.getProperty("headless"));
        if (System.getProperty("baseUrl") != null)
            prop.setProperty("baseUrl", System.getProperty("baseUrl"));

        return prop;
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        prop = initializeProperties();
        DriverFactory.initDriver(prop);

        logger.info(">>>> Starting Test: " + method.getName() + " <<<<");

        DriverFactory.getDriver().get(prop.getProperty("baseUrl"));
        logger.info("Browser launched and URL opened: " + prop.getProperty("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String status = result.isSuccess() ? "PASSED" : "FAILED";
        logger.info("<<<< Finished Test: " + result.getMethod().getMethodName() + " | Status: " + status + " >>>>");

        DriverFactory.quitDriver();
        logger.info("Browser closed for thread: " + Thread.currentThread().getId());
        logger.info("-------------------------------------------------------");
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
