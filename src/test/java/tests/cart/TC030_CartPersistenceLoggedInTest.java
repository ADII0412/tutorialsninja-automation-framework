package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC030_CartPersistenceLoggedInTest extends BaseTest {

    @Test(description = "Verify cart persists after logout and login for a registered user")
    public void verifyCartPersistenceAfterLogin() {

        logger.info("===== Starting TC030_CartPersistenceLoggedInTest =====");

        HomePage homePage = new HomePage(getDriver());

        logger.info("Logging in with valid credentials");
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        logger.info("clearing cart before starting with test");
        CartPage cart = homePage.navigateToCart();
        cart.clearCart();
        Assert.assertTrue(cart.isCartEmpty(), "Cart was not cleared properly");

        homePage = new HomePage(getDriver());
        logger.info("Searching and adding product: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        productPage.addToCart();
        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart"
        );

        logger.info("Navigating to cart and validating product presence");
        CartPage cartPage = homePage.navigateToCart();

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product not present in cart before logout"
        );

        logger.info("Logging out");
        LogoutPage logoutPage = homePage.logout();
        homePage = logoutPage.clickContinue();

        logger.info("Logging in again");
        LoginPage loginPageAgain = homePage.navigateToLogin();
        loginPageAgain.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        logger.info("Navigating to cart after re-login");
        cartPage = homePage.navigateToCart();

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product not present in cart after re-login"
        );

        logger.info("===== TC030_CartPersistenceLoggedInTest PASSED =====");
    }
}