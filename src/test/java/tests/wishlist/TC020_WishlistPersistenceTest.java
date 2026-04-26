package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC020_WishlistPersistenceTest extends BaseTest {
    @Test(description = "Verify wishlist persists after logout and login")
    public void verifyWishlistPersistenceAfterLogoutAndLogin() {
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Setting up clean state: clearing wishlist");
        WishlistPage wishlist = loginPage.navigateToWishlist();
        wishlist.clearWishlist();

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        logger.info("Adding product to wishlist: " + productName);
        productPage.addToWishlist();

        LogoutPage logoutPage = homePage.logout();
        homePage = logoutPage.clickContinue();

        LoginPage loginPageAgain = homePage.navigateToLogin();
        loginPageAgain.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Validating wishlist persistence for user: " + TestData.EXISTING_EMAIL02);

        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Product '" + productName + "' vanished from wishlist after re-login!"
        );
    }
}