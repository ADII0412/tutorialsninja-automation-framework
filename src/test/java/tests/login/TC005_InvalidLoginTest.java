package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC005_InvalidLoginTest extends BaseTest {

    @Test(description = "Verify warning message is displayed for completely invalid credentials")
    public void verifyLoginWithInvalidCredentials() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        logger.info("Attempting login with invalid email and password");
        loginPage.loginWithInvalidCredentials(
                TestData.INVALID_EMAIL,
                TestData.INVALID_PASSWORD
        );

        String warning = loginPage.getWarningMessage();
        Assert.assertTrue(
                warning.contains("No match for E-Mail Address"),
                "Generic login error message was not displayed!"
        );
    }
}