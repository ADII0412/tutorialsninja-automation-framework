package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC032_CheckoutWithValidProductTest extends BaseTest {
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
        logger.info("Setup: Logged in and cleared cart for checkout test.");
    }

    @Test(groups = {"regression", "smoke", "sanity", "critical"}, description = "Verify user can successfully place an order")
    public void verifyCheckoutWithValidProduct() {
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_FOR_CHECKOUT);
        ProductPage productPage = searchPage.openProduct();

        productPage.addToCart();
        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart"
        );

        homePage = new HomePage(getDriver());
        CartPage cartPage = homePage.navigateToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        logger.info("Placing order");
        checkoutPage.placeOrder("Test order");

        Assert.assertTrue(
                checkoutPage.isOrderPlaced(),
                "Order was not placed successfully"
        );
    }
}
