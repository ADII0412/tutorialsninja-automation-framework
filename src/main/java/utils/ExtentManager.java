package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            String reportDir = System.getProperty("user.dir") + File.separator + "reports";
            new File(reportDir).mkdirs();

            String reportPath = reportDir + File.separator + "AutomationReport-" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle("TutorialsNinja Automation Report");
            spark.config().setReportName("Functional Test Execution");
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Application", "TutorialsNinja");
            extent.setSystemInfo("Automation Tool", "Selenium");
            extent.setSystemInfo("Framework", "TestNG");
            extent.setSystemInfo("Author", "Aditya Singh");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}