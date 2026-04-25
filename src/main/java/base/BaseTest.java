package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    protected Properties prop;
    protected final Logger logger = LogManager.getLogger(this.getClass());

    public Properties initializeProperties() {
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/config/config.properties"
        )){
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
        return prop;
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        prop = initializeProperties();
        DriverFactory.initDriver(prop);
        DriverFactory.getDriver().get(prop.getProperty("baseUrl"));
        logger.info("Browser launched and URL opened");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
        logger.info("Browser closed for thread: " + Thread.currentThread().getId());
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}