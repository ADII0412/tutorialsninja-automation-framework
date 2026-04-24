package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC021_MoveProductFromWishlistToCartTest extends BaseTest {

    @Test(description = "Verify product can be moved from wishlist to cart successfully")
    public void verifyMoveProductFromWishlistToCart() {

        logger.info("Starting TC021: Move Product From Wishlist To Cart Test");

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

        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Validating product present in wishlist");
        Assert.assertTrue(
                wishlistPage.isProductPresent(productName),
                "Product not present in wishlist before moving to cart!"
        );

        logger.info("Moving product to cart");
        wishlistPage.moveProductToCartByName(productName);

        Assert.assertFalse(
                wishlistPage.isProductPresent(productName),
                "Product still present in wishlist after moving to cart: " + productName
        );
        logger.info("Validating product present in cart");

        CartPage cartPage = homePage.navigateToCart();

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product not present in cart after moving from wishlist: " + productName
        );

        logger.info("TC021 Passed");
    }
}