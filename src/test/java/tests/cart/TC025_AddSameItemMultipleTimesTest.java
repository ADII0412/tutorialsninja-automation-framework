package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC025_AddSameItemMultipleTimesTest extends BaseTest {

    @Test(description = "Verify quantity increases when same product is added multiple times")
    public void verifyAddSameItemMultipleTimes() {
        HomePage homePage = new HomePage(getDriver());
        // Start from a clean cart to make quantity assertion deterministic
        homePage.navigateToCart().clearCart();
        homePage = new HomePage(getDriver());

        logger.info("Searching product: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        logger.info("Adding same product twice to cart");

        productPage.addToCart();
        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "First add to cart failed"
        );

        productPage.addToCart();
        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Second add to cart failed"
        );

        logger.info("Navigating to Cart page");
        CartPage cartPage = homePage.navigateToCart();

        String quantity = cartPage.getProductQuantity(productName);

        logger.info("Validating product quantity");
        Assert.assertNotNull(quantity, "Product was not found in cart: " + productName);

        Assert.assertEquals(
                Integer.parseInt(quantity),
                2,
                "Product quantity did not match expected value"
        );
    }
}