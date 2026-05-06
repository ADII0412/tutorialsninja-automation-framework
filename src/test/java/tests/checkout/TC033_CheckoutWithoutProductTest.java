package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;

public class TC033_CheckoutWithoutProductTest extends BaseTest {

    @Test(groups = {"regression", "negative"}, description = "Verify user cannot proceed to checkout with empty cart")
    public void verifyCheckoutWithoutProduct() {
        HomePage homePage = new HomePage(getDriver());

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
