package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {
    public static String captureScreenshot(WebDriver driver, String testName) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testName + "_" + timestamp + ".png";

        Path screenshotsDir = Paths.get(System.getProperty("user.dir"), "screenshots");
        Path screenshotPath = screenshotsDir.resolve(screenshotName);

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.forceMkdir(screenshotsDir.toFile());
            FileUtils.copyFile(source, screenshotPath.toFile());

        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + screenshotPath, e);
        }

        return "../screenshots/" + screenshotName;
    }
}
