package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC022_AddToWishlistWithoutLoginTest extends BaseTest {

    @Test(description = "Verify guest user is redirected to login when adding to wishlist and product persists after login")
    public void verifyAddToWishlistWithoutLoginRedirectAndPersistence() {

        logger.info("Starting TC022: Add To Wishlist Without Login Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Clearing wishlist before test");
        WishlistPage wishlist = loginPage.navigateToWishlist();
        wishlist.clearWishlist();
        LogoutPage logout = wishlist.logout();
        logout.clickContinue();

        homePage = new HomePage(getDriver());

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        logger.info("Attempting to add product to wishlist without login");

        productPage.addToWishlist();

        logger.info("Validating redirection to login page");
        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("route=account/login"),
                "User was not redirected to login page!"
        );

        logger.info("Logging in after redirect");

        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Navigating to wishlist");

        WishlistPage wishlistPage = new HomePage(getDriver()).navigateToWishlist();
        logger.info("Validating product persistence in wishlist");

        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Product not present in wishlist after login: " + productName
        );

        logger.info("TC022 Passed");
    }
}