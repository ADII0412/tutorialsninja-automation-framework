package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static WebDriver initDriver(Properties prop) {

        String browser = prop.getProperty("browser", "chrome").toLowerCase();

        logger.info("Launching browser: {} | Thread ID: {}", browser, Thread.currentThread().getId());

        switch (browser) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();

                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    chromeOptions.addArguments("--headless=new");
                }

                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");

                tlDriver.set(new ChromeDriver(chromeOptions));
                break;


            case "firefox":
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }

                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");

                tlDriver.set(new FirefoxDriver(firefoxOptions));
                break;


            case "edge":
                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                if (Boolean.parseBoolean(prop.getProperty("headless", "false"))) {
                    edgeOptions.addArguments("--headless=new");
                }

                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-notifications");

                tlDriver.set(new EdgeDriver(edgeOptions));
                break;


            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        //Common settings
        getDriver().manage().deleteAllCookies();

        int pageLoadTime = Integer.parseInt(prop.getProperty("pageLoadTimeout", "20"));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));

        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}