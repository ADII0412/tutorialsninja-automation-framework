package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC033_CheckoutWithoutAgreeingTermsTest extends BaseTest {
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setupCartForCheckout() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);
        // Recreate page object after login redirect to prevent stale references
        homePage = new HomePage(getDriver());

        CartPage cart = homePage.navigateToCart();
        cart.clearCart();

        homePage = new HomePage(getDriver());
        homePage.searchProduct(TestData.PRODUCT_FOR_CHECKOUT).openProduct().addToCart();
        homePage = new HomePage(getDriver());
        this.checkoutPage = homePage.navigateToCart().proceedToCheckout();

        logger.info("Setup: Logged in and reached checkout with " + TestData.PRODUCT_FOR_CHECKOUT);
    }

    @Test(description = "Verify warning when terms and conditions are not accepted")
    public void verifyCheckoutWithoutTerms() {
        logger.info("Navigating through billing and delivery details...");
        checkoutPage.continueBillingDetails();
        checkoutPage.continueDeliveryDetails();

        logger.info("Selecting delivery method...");
        checkoutPage.continueDeliveryMethod("");

        logger.info("Attempting to proceed without agreeing to Terms & Conditions...");
        checkoutPage.clickPaymentMethodContinueWithoutTerms();

        String warning = checkoutPage.getTermsWarning();
        logger.info("Captured warning message: " + warning);

        Assert.assertTrue(
                warning.contains("Warning: You must agree to the Terms & Conditions!"),
                "Expected terms warning not displayed! Actual: " + warning
        );
    }
}