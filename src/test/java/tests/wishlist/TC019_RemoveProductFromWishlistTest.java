package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC019_RemoveProductFromWishlistTest extends BaseTest {

    private HomePage homePage;
    private String productName;

    @BeforeMethod
    public void setupWishlistWithProduct() {
        homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);
        WishlistPage wishlist = homePage.navigateToWishlist();
        wishlist.clearWishlist();
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productName = productPage.getProductTitle();
        productPage.addToWishlist();

        logger.info("Setup complete: Logged in and product '" + productName + "' added to wishlist.");
    }

    @Test(description = "Verify user can remove a product from wishlist successfully")
    public void verifyRemoveProductFromWishlist() {
        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Removing product from wishlist: " + productName);
        wishlistPage.removeProductByName(productName);

        Assert.assertFalse(
                wishlistPage.isProductPresent(productName),
                "Critical Failure: Product '" + productName + "' still exists in wishlist after deletion!"
        );
    }
}