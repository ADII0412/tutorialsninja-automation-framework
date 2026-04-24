package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC019_RemoveProductFromWishlistTest extends BaseTest {

    @Test(description = "Verify user can remove a product from wishlist successfully")
    public void verifyRemoveProductFromWishlist() {

        logger.info("Starting TC019: Remove Product From Wishlist Test");

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
        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Validating product is added to wishlist");

        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Product not added to wishlist!"
        );

        logger.info("Removing product from wishlist");

        wishlistPage.removeProductByName(productName);

        logger.info("Validating product is removed from wishlist");

        Assert.assertFalse(
                wishlistPage.isProductPresent(productName),
                "Product still present in wishlist after removal: " + productName
        );

        logger.info("TC019 Passed");
    }
}