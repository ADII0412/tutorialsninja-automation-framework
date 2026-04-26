package tests.registration;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.TestData;

public class TC003_PrivacyPolicyMandatoryTest extends BaseTest {

    @Test(description = "Verify privacy policy checkbox is mandatory during registration")
    public void verifyPrivacyPolicyCheckboxMandatory() {
        HomePage homePage = new HomePage(getDriver());
        RegisterPage registerPage = homePage.navigateToRegister();

        String email = TestData.generateEmail();

        registerPage.submitWithoutPrivacyPolicy(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                email,
                TestData.PHONE,
                TestData.PASSWORD
        );

        logger.info("Validating privacy policy warning");

        String warning = registerPage.getPrivacyPolicyWarning();

        Assert.assertTrue(
                warning.contains("You must agree"),
                "Privacy policy warning not displayed!"
        );
    }
}