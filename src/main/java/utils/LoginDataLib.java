package utils;

import org.testng.annotations.DataProvider;

public class LoginDataLib {

    @DataProvider(name = "loginScenarios")
    public static Object[][] getLoginData() {
        return new Object[][] {
                // { Email, Password, Expected Success, Scenario Description }
                { TestData.EXISTING_EMAIL, TestData.PASSWORD, true, "Valid Credentials" },
                { TestData.INVALID_EMAIL, TestData.INVALID_PASSWORD, false, "Invalid Credentials" },
                { TestData.EXISTING_EMAIL, TestData.INVALID_PASSWORD, false, "Security: Invalid Password" }
        };
    }
}