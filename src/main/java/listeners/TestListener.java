package listeners;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.*;
import utils.ExtentManager;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName() + "_" + System.currentTimeMillis();
        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.getDriver();

        String screenshotPath = null;
        try {
            screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName);
        } catch (Exception e) {
            logger.error("Screenshot capture failed: " + e.getMessage());
        }

        logger.error("Test FAILED: " + testName, result.getThrowable());

        if (test.get() != null) {
            test.get().fail(result.getThrowable());
            if (screenshotPath != null) {
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        logger.warn("Test SKIPPED: " + testName);

        String screenshotPath = null;

        if (result.getInstance() instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) result.getInstance();
            WebDriver driver = baseTest.getDriver();
            if (driver != null) {
                try {
                    screenshotPath = ScreenshotUtil.captureScreenshot(driver, testName);
                } catch (Exception e) {
                    logger.error("Screenshot capture failed: " + e.getMessage());
                }
            }
        }

        if (test.get() != null) {
            test.get().skip(result.getThrowable());
            if (screenshotPath != null) {
                test.get().addScreenCaptureFromPath(screenshotPath);
            }
        }
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}