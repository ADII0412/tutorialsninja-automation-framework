package tests.registration;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.TestData;

public class TC002_DuplicateEmailRegistrationTest extends BaseTest {

    @Test(description = "Verify warning is shown when registering with existing email")
    public void verifyDuplicateEmailRegistrationShowsWarning() {

        logger.info("Starting TC002: Duplicate Email Registration");

        HomePage homePage = new HomePage(getDriver());
        RegisterPage registerPage = homePage.navigateToRegister();

        registerPage.fillRegistrationForm(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.EXISTING_EMAIL,
                TestData.PHONE,
                TestData.PASSWORD
        );
        registerPage.checkPrivacyPolicy();
        registerPage.clickContinueButton();

        logger.info("Validating duplicate email warning");

        String warning = registerPage.getDuplicateEmailWarning();

        Assert.assertTrue(
                warning.contains("already registered"),
                "Duplicate email warning not displayed!"
        );

        logger.info("TC002 Passed");
    }
}