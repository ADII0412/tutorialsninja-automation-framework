package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC005_InvalidLoginTest extends BaseTest {

    @Test(description = "Verify warning message is displayed for invalid login credentials")
    public void verifyLoginWithInvalidCredentials() {

        logger.info("Starting TC005: Invalid Login Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        loginPage.loginWithInvalidCredentials(
                TestData.INVALID_EMAIL,
                TestData.INVALID_PASSWORD
        );

        logger.info("Validating warning message");

        String warning = loginPage.getWarningMessage();

        Assert.assertTrue(
                warning.contains("No match for E-Mail Address"),
                "Warning message not displayed for invalid login!"
        );

        logger.info("TC005 Passed");
    }
}