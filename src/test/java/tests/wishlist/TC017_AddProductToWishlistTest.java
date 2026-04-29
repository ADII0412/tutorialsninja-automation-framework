package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC017_AddProductToWishlistTest extends BaseTest {
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeMethod
    public void setupWishlistState() {
        homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Setup: Clearing wishlist for " + TestData.EXISTING_EMAIL02);
        WishlistPage wishlist = homePage.navigateToWishlist();
        wishlist.clearWishlist();
    }

    @Test(description = "Verify user can add product to wishlist successfully")
    public void verifyAddProductToWishlist() {
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        productPage = searchPage.openProduct();
        String expectedProductName = productPage.getProductTitle();

        logger.info("Adding product to wishlist: " + expectedProductName);
        productPage.addToWishlist();

        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("wish list"),
                "Success message for wishlist addition not displayed!"
        );

        WishlistPage wishlistPage = homePage.navigateToWishlist();
        Assert.assertTrue(
                wishlistPage.isProductPresent(expectedProductName),
                "Product '" + expectedProductName + "' was not found in the wishlist page!"
        );
    }
}