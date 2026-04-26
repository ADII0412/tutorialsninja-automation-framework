package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC030_CartPersistenceLoggedInTest extends BaseTest {

    private HomePage homePage;
    private String productName;

    @BeforeMethod
    public void setupCartState() {
        homePage = new HomePage(getDriver());

        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        CartPage cart = homePage.navigateToCart();
        cart.clearCart();

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productName = productPage.getProductTitle();
        productPage.addToCart();

        logger.info("Setup complete: Cart cleared and '" + productName + "' added for persistence test.");
    }

    @Test(description = "Verify cart contents persist across different sessions for the same user")
    public void verifyCartPersistenceAfterLogin() {
        LogoutPage logoutPage = homePage.logout();
        homePage = logoutPage.clickContinue();

        LoginPage loginPageAgain = homePage.navigateToLogin();
        loginPageAgain.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        CartPage cartPage = homePage.navigateToCart();
        logger.info("Validating persistence of '" + productName + "' after re-login.");

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Persistence Failure: Cart was empty or item was lost after re-login for user: " + TestData.EXISTING_EMAIL03
        );
    }
}