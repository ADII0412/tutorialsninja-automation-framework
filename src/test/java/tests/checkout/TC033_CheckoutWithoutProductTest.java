package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class TC033_CheckoutWithoutProductTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setupCheckoutPreconditions() {
        homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        homePage = new HomePage(getDriver());
        CartPage cartPage = homePage.navigateToCart();
        cartPage.clearCart();

        homePage = new HomePage(getDriver());
        logger.info("Setup: Logged in and cleared cart for empty checkout test.");
    }

    @Test(groups = {"regression", "negative"}, description = "Verify user cannot proceed to checkout with empty cart")
    public void verifyCheckoutWithoutProduct() {
        CartPage cartPage = homePage.navigateToCart();

        logger.info("Validating cart is empty");

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart is not empty"
        );

        logger.info("Attempting to proceed to checkout");

        boolean canCheckout = cartPage.canProceedToCheckout();

        Assert.assertFalse(
                canCheckout,
                "User should not be able to checkout with empty cart"
        );
    }
}
