package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ForgotPasswordPage;
import pages.HomePage;
import pages.LoginPage;

public class TC005_ForgotPasswordNavigationTest extends BaseTest {
    @Test(description = "Verify user can navigate to Forgot Password page from Login page")
    public void verifyForgotPasswordNavigation() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        ForgotPasswordPage forgotPasswordPage = loginPage.navigateToForgotPassword();

        logger.info("Validating Forgot Password page navigation");

        Assert.assertTrue(
                forgotPasswordPage.isForgotPasswordPageDisplayed(),
                "Failed to navigate to Forgot Password page!"
        );
    }
}