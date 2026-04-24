package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC024_AddMultipleItemsToCartTest extends BaseTest {

    @Test(description = "Verify user can add multiple products to cart")
    public void verifyAddMultipleItemsToCart() {

        logger.info("===== Starting TC024_AddMultipleItemsToCartTest =====");

        HomePage homePage = new HomePage(getDriver());

        logger.info("Adding first product: " + TestData.PRODUCT_1);
        SearchPage searchPage1 = homePage.searchProduct(TestData.PRODUCT_1);
        ProductPage productPage1 = searchPage1.openProduct();
        String product1 = productPage1.getProductTitle();
        productPage1.addToCart();

        Assert.assertTrue(
                productPage1.getSuccessMessage().toLowerCase().contains("success"),
                "First product not added to cart!"
        );

        logger.info("Adding second product: " + TestData.PRODUCT_2);
        SearchPage searchPage2 = homePage.searchProduct(TestData.PRODUCT_2);
        ProductPage productPage2 = searchPage2.openProduct();
        String product2 = productPage2.getProductTitle();
        productPage2.addToCart();

        Assert.assertTrue(
                productPage2.getSuccessMessage().toLowerCase().contains("success"),
                "Second product not added to cart!"
        );

        logger.info("Navigating to Cart page");

        CartPage cartPage = homePage.navigateToCart();

        logger.info("Validating cart item count");
        Assert.assertEquals(
                cartPage.getCartItemCount(),
                2,
                "Cart item count mismatch!"
        );

        logger.info("Validating both products are present in cart");

        Assert.assertTrue(
                cartPage.isProductPresentInCart(product1),
                "First product not found in cart!"
        );

        Assert.assertTrue(
                cartPage.isProductPresentInCart(product2),
                "Second product not found in cart!"
        );

        logger.info("===== TC024_AddMultipleItemsToCartTest PASSED =====");
    }
}