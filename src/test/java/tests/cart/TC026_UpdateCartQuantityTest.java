package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC026_UpdateCartQuantityTest extends BaseTest {

    @Test(description = "Verify user can update product quantity in cart")
    public void TC026_verifyUpdateCartQuantity() {

        logger.info("===== Starting TC026_UpdateCartQuantityTest =====");

        HomePage homePage = new HomePage(getDriver());

        logger.info("Searching and adding product: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();
        productPage.addToCart();

        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart"
        );

        logger.info("Navigating to Cart page");
        CartPage cartPage = homePage.navigateToCart();

        int expectedQty = 3;
        logger.info("Updating product quantity to: " + expectedQty);

        cartPage.updateProductQuantity(productName, String.valueOf(expectedQty));

        String updatedQty = cartPage.getProductQuantity(productName);

        logger.info("Validating updated quantity");

        Assert.assertEquals(
                Integer.parseInt(updatedQty),
                expectedQty,
                "Product quantity update failed"
        );

        logger.info("===== TC026_UpdateCartQuantityTest PASSED =====");
    }
}