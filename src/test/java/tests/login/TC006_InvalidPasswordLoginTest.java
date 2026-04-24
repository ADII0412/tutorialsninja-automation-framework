package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC006_InvalidPasswordLoginTest extends BaseTest {

    @Test(description = "Verify warning is shown when valid email but invalid password is used")
    public void verifyLoginWithInvalidPassword() {

        logger.info("Starting TC006: Invalid Password Login Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        loginPage.loginWithInvalidCredentials(
                TestData.EXISTING_EMAIL,
                TestData.INVALID_PASSWORD
        );

        logger.info("Validating warning message");

        String warning = loginPage.getWarningMessage();

        Assert.assertTrue(
                warning.contains("No match for E-Mail Address"),
                "Warning message not displayed for invalid password!"
        );

        logger.info("TC006 Passed");
    }
}