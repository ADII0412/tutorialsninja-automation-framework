package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC033_CheckoutWithoutAgreeingTermsTest extends BaseTest {

    @Test(description = "Verify checkout fails if terms are not agreed")
    public void verifyCheckoutWithoutAgreeingTerms() {
        HomePage homePage = new HomePage(getDriver());

        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        productPage.addToCart();

        CartPage cartPage = homePage.navigateToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        logger.info("Proceeding checkout without agreeing terms");

        checkoutPage.continueBillingDetails();
        checkoutPage.continueDeliveryDetails();
        checkoutPage.continueDeliveryMethod("Test");

        checkoutPage.confirmOrder();

        Assert.assertTrue(
                checkoutPage.getTermsWarning().contains("Warning"),
                "Warning not displayed when terms not agreed"
        );
    }
}