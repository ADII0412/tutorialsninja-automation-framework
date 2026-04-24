package tests.wishlist;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

public class TC018_AddMultipleProductsToWishlistTest extends BaseTest {

    @Test(description = "Verify user can add multiple products to wishlist")
    public void verifyAddMultipleProductsToWishlist() {

        logger.info("Starting TC018: Add Multiple Products To Wishlist Test");

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = homePage.navigateToLogin();
        loginPage.login(TestData.EXISTING_EMAIL02, TestData.PASSWORD);

        logger.info("Clearing wishlist before test");
        WishlistPage wishlist = loginPage.navigateToWishlist();
        wishlist.clearWishlist();

        homePage = new HomePage(getDriver());

        List<String> expectedProducts = new ArrayList<>();

        SearchPage searchPage1 = homePage.searchProduct(TestData.PRODUCT_1);
        ProductPage productPage1 = searchPage1.openProduct();
        expectedProducts.add(productPage1.getProductTitle());
        productPage1.addToWishlist();

        SearchPage searchPage2 = homePage.searchProduct(TestData.PRODUCT_2);
        ProductPage productPage2 = searchPage2.openProduct();
        expectedProducts.add(productPage2.getProductTitle());
        productPage2.addToWishlist();

        logger.info("Navigating to wishlist page");

        WishlistPage wishlistPage = homePage.navigateToWishlist();

        logger.info("Validating all products are present in wishlist");

        for (String product : expectedProducts) {
            Assert.assertTrue(
                    wishlistPage.isProductPresent(product),
                    "Product not found in wishlist: " + product
            );
        }

        logger.info("Validating wishlist item count");

        Assert.assertEquals(
                wishlistPage.getWishlistItemCount(),
                expectedProducts.size(),
                "Wishlist item count mismatch!"
        );

        logger.info("TC018 Passed");
    }
}