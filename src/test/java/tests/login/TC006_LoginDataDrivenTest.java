package tests.login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.LoginDataLib;

public class TC006_LoginDataDrivenTest extends BaseTest {

    @Test(
            dataProvider = "loginScenarios",
            dataProviderClass = LoginDataLib.class,
            description = "Verify login functionality with valid, invalid, and security scenarios"
    )
    public void verifyLoginScenarios(String email, String password, boolean shouldSucceed, String scenario) {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();

        logger.info("Testing Scenario: " + scenario + " | User: " + email);
        loginPage.login(email, password);

        if (shouldSucceed) {
            Assert.assertTrue(getDriver().getCurrentUrl().contains("account/account"),
                    "Login failed for valid credentials in scenario: " + scenario);
        } else {
            String warning = loginPage.getWarningMessage();
            Assert.assertTrue(warning.contains("No match"),
                    "Expected warning not found for scenario: " + scenario);

            if (scenario.contains("Security")) {
                Assert.assertEquals(warning, "Warning: No match for E-Mail Address and/or Password.",
                        "Security Breach: System revealed account existence!");
            }
        }
    }
}