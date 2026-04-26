package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC024_AddMultipleItemsToCartTest extends BaseTest {
    @Test(description = "Verify user can add multiple products to cart")
    public void verifyAddMultipleItemsToCart() {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent header = new HeaderComponent(getDriver());

        int initialCount = header.getCartCount();
        logger.info("Initial cart count: " + initialCount);

        logger.info("Adding first product: " + TestData.PRODUCT_1);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_1);
        ProductPage productPage = searchPage.openProduct();
        String product1 = productPage.getProductTitle();
        productPage.addToCart();

        logger.info("Adding second product: " + TestData.PRODUCT_2);
        homePage.searchProduct(TestData.PRODUCT_2);
        productPage = searchPage.openProduct();
        String product2 = productPage.getProductTitle();
        productPage.addToCart();

        CartPage cartPage = homePage.navigateToCart();

        int expectedCount = initialCount + 2;
        int actualCount = cartPage.getCartItemCount();

        logger.info("Validating cart item count. Expected: " + expectedCount);

        Assert.assertEquals(
                actualCount,
                expectedCount,
                "Cart item count mismatch! Likely due to pre-existing items."
        );

        Assert.assertTrue(cartPage.isProductPresentInCart(product1), "Product 1 missing: " + product1);
        Assert.assertTrue(cartPage.isProductPresentInCart(product2), "Product 2 missing: " + product2);
    }
}