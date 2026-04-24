package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC020_WishlistPersistenceTest extends BaseTest {

    @Test(description = "Verify wishlist persists after logout and login")
    public void verifyWishlistPersistenceAfterLogoutAndLogin() {

        logger.info("Starting TC020: Wishlist Persistence Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Clearing wishlist before test");
        WishlistPage wishlist = loginPage.navigateToWishlist();
        wishlist.clearWishlist();

        homePage = new HomePage(getDriver());

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        logger.info("Adding product to wishlist: " + productName);
        productPage.addToWishlist();

        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("wish list"),
                "Product was not added to wishlist!"
        );

        logger.info("Logging out");
        LogoutPage logoutPage = homePage.logout();
        homePage = logoutPage.clickContinue();

        logger.info("Logging in again");
        LoginPage loginPageAgain = homePage.navigateToLogin();
        loginPageAgain.login(TestData.EXISTING_EMAIL, TestData.PASSWORD);
        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Validating wishlist persistence");

        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Product not present in wishlist after re-login: " + productName
        );

        logger.info("TC020 Passed");
    }
}