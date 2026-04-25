package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC028_EmptyCartValidationTest extends BaseTest {

    @Test(description = "Verify cart is empty after removing all items")
    public void verifyEmptyCartValidation() {

        logger.info("===== Starting TC028_EmptyCartValidationTest =====");

        HomePage homePage = new HomePage(getDriver());

        logger.info("Searching and adding product: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productPage.addToCart();

        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart"
        );

        logger.info("Navigating to Cart page");
        CartPage cartPage = homePage.navigateToCart();

        logger.info("Clearing all items from cart");
        cartPage.clearCart();

        logger.info("Validating cart is empty");

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart is not empty"
        );

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                0,
                "Cart item count is not zero"
        );

        logger.info("===== TC028_EmptyCartValidationTest PASSED =====");
    }
}