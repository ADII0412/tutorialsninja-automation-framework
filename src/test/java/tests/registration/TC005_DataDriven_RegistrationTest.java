package tests.registration;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountSuccessPage;
import pages.HomePage;
import pages.RegisterPage;
import utils.ExcelUtil;
import utils.TestData;

public class TC005_DataDriven_RegistrationTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        String filePath = System.getProperty("user.dir")
                + "/src/test/resources/testdata/RegistrationData.xlsx";
        return ExcelUtil.getTestData(filePath, "RegistrationTests");
    }

    @Test(dataProvider = "registrationData", description = "Verify registration scenarios using Excel data")
    public void verifyRegistrationUsingExcelData(
            String firstName,
            String lastName,
            String email,
            String phone,
            String password,
            String expectedResult
    ) {
        String resolvedEmail = "UNIQUE".equalsIgnoreCase(email) ? TestData.generateEmail() : email;
        logger.info("Testing registration scenario. Email: " + resolvedEmail + ", ExpectedResult: " + expectedResult);

        HomePage homePage = new HomePage(getDriver());
        RegisterPage registerPage = homePage.navigateToRegister();

        if ("PASS".equalsIgnoreCase(expectedResult)) {
            AccountSuccessPage successPage = registerPage.registerAccount(
                    firstName,
                    lastName,
                    resolvedEmail,
                    phone,
                    password
            );

            Assert.assertTrue(
                    successPage.getSuccessMessage().contains("Your Account Has Been Created"),
                    "Account creation failed for valid registration data"
            );
            return;
        }

        if (TestData.EXISTING_EMAIL.equalsIgnoreCase(resolvedEmail)) {
            registerPage.fillRegistrationForm(firstName, lastName, resolvedEmail, phone, password);
            registerPage.checkPrivacyPolicy();
            registerPage.clickContinueButton();

            Assert.assertTrue(
                    registerPage.getDuplicateEmailWarning().contains("already registered"),
                    "Duplicate email warning not displayed"
            );
            return;
        }

        registerPage.registerUserWithPasswordMismatch(
                firstName,
                lastName,
                resolvedEmail,
                phone,
                password,
                password + "Mismatch"
        );

        Assert.assertEquals(
                registerPage.getPasswordMismatchErrorMessage(),
                "Password confirmation does not match password!",
                "Password mismatch error message not displayed correctly"
        );
    }
}
