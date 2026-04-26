package tests.registration;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountSuccessPage;
import pages.HomePage;
import pages.RegisterPage;
import utils.TestData;

public class TC001_RegisterWithMandatoryFieldsTest extends BaseTest {
    @Test(description = "Verify user can register with mandatory fields")
    public void verifyRegisterWithMandatoryFields() {
        HomePage homePage = new HomePage(getDriver());
        RegisterPage registerPage = homePage.navigateToRegister();

        String email = TestData.generateEmail();

        AccountSuccessPage successPage = registerPage.registerAccount(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                email,
                TestData.PHONE,
                TestData.PASSWORD
        );

        logger.info("Registration completed, validating success message");

        Assert.assertTrue(
                successPage.getSuccessMessage().contains("Your Account Has Been Created"),
                "Account creation failed!"
        );
    }

}
