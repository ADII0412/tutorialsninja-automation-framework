package tests.registration;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;

public class TC004_RegistrationPasswordMismatchTest extends BaseTest {
    @Test(description = "Verify error message when password and confirm password do not match")
    public void verifyPasswordMismatchError() {
        HomePage homePage = new HomePage(getDriver());
        RegisterPage registerPage = homePage.navigateToRegister();

        logger.info("Registering with mismatched passwords");
        registerPage.registerUserWithPasswordMismatch(
                "Aditya", "Singh",
                "mismatch_" + System.currentTimeMillis() + "@test.com",
                "9876543210",
                "Password123",
                "WrongPassword456"
        );

        String error = registerPage.getPasswordMismatchErrorMessage();

        Assert.assertEquals(
                error,
                "Password confirmation does not match password!",
                "Mismatch error message not displayed correctly!"
        );
    }
}
