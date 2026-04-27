package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC024_AddMultipleItemsToCartTest extends BaseTest {

    @Test(description = "Verify user can add multiple distinct products to cart")
    public void verifyAddMultipleItemsToCart() {
        HomePage homePage = new HomePage(getDriver());
        HeaderComponent header = new HeaderComponent(getDriver());

        int initialCount = header.getCartCount();

        logger.info("Searching and adding first product: " + TestData.PRODUCT_1);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_1);
        ProductPage productPage = searchPage.openProduct();
        String product1 = productPage.getProductTitle();
        productPage.addToCart();

        logger.info("Searching and adding second product: " + TestData.PRODUCT_2);
        searchPage = homePage.searchProduct(TestData.PRODUCT_2);
        productPage = searchPage.openProduct();
        String product2 = productPage.getProductTitle();
        productPage.addToCart();

        CartPage cartPage = homePage.navigateToCart();

        int expectedCount = initialCount + 2;
        int actualCount = cartPage.getCartItemCount();

        Assert.assertEquals(actualCount, expectedCount, "Cart item count mismatch!");
        Assert.assertTrue(cartPage.isProductPresentInCart(product1), "Product 1 missing: " + product1);
        Assert.assertTrue(cartPage.isProductPresentInCart(product2), "Product 2 missing: " + product2);

        Assert.assertNotEquals(product1, product2, "Test Logic Error: Added the same product twice instead of distinct items!");
    }
}