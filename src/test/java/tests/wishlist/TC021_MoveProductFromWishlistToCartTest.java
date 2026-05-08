package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC021_MoveProductFromWishlistToCartTest extends BaseTest {

    private HomePage homePage;
    private String productName;

    @BeforeMethod(alwaysRun = true)
    public void setupWishlistState() {
        homePage = new HomePage(getDriver());

        if (getDriver() == null) {
            throw new RuntimeException("Driver initialization failed in BaseTest!");
        }

        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        WishlistPage wishlist = homePage.navigateToWishlist();
        wishlist.clearWishlist();

        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productName = productPage.getProductTitle();
        productPage.addToWishlist();

        logger.info("Setup: Logged in and '" + productName + "' added to wishlist.");
    }

    @Test(groups = {"regression", "e2e"}, description = "Verify product can be moved from wishlist to cart successfully")
    public void verifyMoveProductFromWishlistToCart(){
        Assert.assertNotNull(homePage, "HomePage was not initialized in @BeforeMethod!");

        WishlistPage wishlistPage = homePage.navigateToWishlist();
        logger.info("Action: Moving product to cart: " + productName);
        wishlistPage.moveProductToCartByName(productName);

        Assert.assertTrue(wishlistPage.isWishlistEmpty(), "Product should have been removed from wishlist!");

        CartPage cartPage = homePage.navigateToCart();
        Assert.assertTrue(cartPage.isProductPresentInCart(productName), "Product missing from cart after transfer!");
    }
}