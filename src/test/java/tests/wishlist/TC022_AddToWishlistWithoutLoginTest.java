package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC022_AddToWishlistWithoutLoginTest extends BaseTest {

    private HomePage homePage;
    private String productName;

    @BeforeMethod
    public void setupCleanWishlist() {
        homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlist = homePage.navigateToWishlist();
        wishlist.clearWishlist();

        homePage.logout().clickContinue();

        logger.info("Setup complete: Wishlist cleared for " + TestData.EXISTING_EMAIL02 + " and user logged out.");
    }

    @Test(description = "Verify guest user is redirected to login and product persists in wishlist")
    public void verifyAddToWishlistWithoutLoginRedirectAndPersistence() {
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productName = productPage.getProductTitle();

        logger.info("Guest adding product to wishlist: " + productName);
        productPage.addToWishlist();

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("account/login"),
                "Guest was not redirected to login page after adding to wishlist!"
        );

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlistPage = homePage.navigateToWishlist();
        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Persistence Failure: Product '" + productName + "' was lost after login redirect!"
        );
    }
}