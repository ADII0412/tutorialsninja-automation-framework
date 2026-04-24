package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC017_AddProductToWishlistTest extends BaseTest {

    @Test(description = "Verify user can add product to wishlist and it appears in wishlist page")
    public void verifyAddProductToWishlist() {

        logger.info("Starting TC017: Add Product To Wishlist Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL03, TestData.PASSWORD);

        logger.info("Clearing wishlist before test");
        WishlistPage wishlist = loginPage.navigateToWishlist();
        wishlist.clearWishlist();

        homePage = new HomePage(getDriver());

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String expectedProductName = productPage.getProductTitle();

        logger.info("Adding product to wishlist");
        productPage.addToWishlist();
        String successMsg = productPage.getSuccessMessage();

        Assert.assertTrue(
                successMsg.toLowerCase().contains("wish list"),
                "Product was not added to wishlist successfully!"
        );

        logger.info("Navigating to wishlist page");
        WishlistPage wishlistPage = homePage.navigateToWishlist();

        Assert.assertTrue(
                wishlistPage.isProductPresent(expectedProductName),
                "Expected product is not present in wishlist!"
        );

        logger.info("TC017 Passed");
    }
}