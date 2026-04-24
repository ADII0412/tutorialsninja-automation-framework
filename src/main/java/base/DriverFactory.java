package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Properties;

public class DriverFactory {

    // ThreadLocal for parallel execution
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    // Initialize Driver
    public static WebDriver initDriver(Properties prop) {

        String browser = prop.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver());
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                tlDriver.set(new FirefoxDriver());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                tlDriver.set(new EdgeDriver());
                break;

            default:
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();

        int pageLoadTime = Integer.parseInt(prop.getProperty("pageLoadTimeout", "20"));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
        getDriver().get(prop.getProperty("baseUrl"));
        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}