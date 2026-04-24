package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testName + "_" + timestamp + ".png";

        String screenshotPath = System.getProperty("user.dir")
                + "/screenshots/" + screenshotName;

        try {
            // Take screenshot
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Create destination file
            File destination = new File(screenshotPath);

            // Copy file
            FileUtils.copyFile(source, destination);

        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + screenshotPath, e);
        }

        return screenshotPath;
    }
}