package tests.checkout;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC031_CheckoutWithValidProductTest extends BaseTest {

    @Test(description = "Verify user can successfully place an order")
    public void verifyCheckoutWithValidProduct() {
        HomePage homePage = new HomePage(getDriver());

        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_FOR_CHECKOUT);
        ProductPage productPage = searchPage.openProduct();

        productPage.addToCart();
        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart"
        );

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