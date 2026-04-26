package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC023_AddToCartTest extends BaseTest {

    @Test(description = "Verify user can add a product to cart successfully")
    public void verifyAddToCart() {
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        String productName = productPage.getProductTitle();

        logger.info("Adding product to cart: " + productName);
        productPage.addToCart();

        Assert.assertTrue(
                productPage.getSuccessMessage().toLowerCase().contains("success"),
                "Product was not added to cart successfully!"
        );

        CartPage cartPage = productPage.clickCartButton();

        logger.info("Validating presence of '" + productName + "' in cart");

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product was missing from the cart table: " + productName
        );
    }
}