package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC023_AddToWishlistWithoutLoginTest extends BaseTest {

    private HomePage homePage;
    private String productName;

    @BeforeMethod(alwaysRun = true)
    public void setupCleanWishlist() {
        homePage = new HomePage(getDriver());

        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlist = homePage.navigateToWishlist();
        wishlist.clearWishlist();

        LogoutPage logout = homePage.logout();

        logout.clickContinue();
        homePage = new HomePage(getDriver());

        logger.info("Setup complete: Wishlist cleared and user logged out.");
    }

    @Test(groups = {"regression", "e2e"}, description = "Verify guest user is redirected to login and product persists in wishlist")
    public void verifyAddToWishlistWithoutLoginRedirectAndPersistence() {
        Assert.assertNotNull(homePage, "HomePage initialization failed!");

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productName = productPage.getProductTitle();

        logger.info("Guest adding product to wishlist: " + productName);
        productPage.addToWishlist();

        logger.info("Navigating to Wishlist page");
        homePage.navigateToWishlist();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("account/login"), "Guest not redirected to login!");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlistPage = homePage.navigateToWishlist();
        Assert.assertTrue(wishlistPage.isProductPresent(productName), "Persistence Failure: Product lost after login!");
    }
}