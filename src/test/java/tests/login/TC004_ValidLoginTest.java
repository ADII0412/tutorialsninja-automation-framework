package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC004_ValidLoginTest extends BaseTest {

    @Test(description = "Verify user can login with valid credentials")
    public void verifyLoginWithValidCredentials() {

        logger.info("Starting TC004: Valid Login Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        AccountPage accountPage = loginPage.login(
                TestData.EXISTING_EMAIL,
                TestData.PASSWORD
        );

        logger.info("Validating successful login");

        Assert.assertTrue(
                accountPage.isEditAccountInfoDisplayed(),
                "Login failed - Account page not displayed"
        );

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("route=account/account"),
                "URL validation failed after login"
        );

        logger.info("TC004 Passed");
    }
}