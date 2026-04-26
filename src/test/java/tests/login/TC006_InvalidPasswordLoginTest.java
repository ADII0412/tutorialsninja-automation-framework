package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC006_InvalidPasswordLoginTest extends BaseTest {

    @Test(description = "Security: Verify system does not reveal valid email existence on password failure")
    public void verifyLoginWithInvalidPassword() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        logger.info("Attempting login with VALID email but INVALID password");
        loginPage.loginWithInvalidCredentials(
                TestData.EXISTING_EMAIL,
                TestData.INVALID_PASSWORD
        );

        String warning = loginPage.getWarningMessage();

        Assert.assertEquals(
                warning,
                "Warning: No match for E-Mail Address and/or Password.",
                "Security Risk: System might be revealing account existence through specific error messages!"
        );
    }
}