package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC027_RemoveItemFromCartTest extends BaseTest {

    @Test(description = "Verify user can remove item from cart successfully")
    public void verifyRemoveItemFromCart() {

        logger.info("===== Starting TC027_RemoveItemFromCartTest =====");

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

        logger.info("Removing product from cart: " + productName);
        cartPage.removeProductByName(productName);

        logger.info("Validating product is removed from cart");

        Assert.assertFalse(
                cartPage.isProductPresentInCart(productName),
                "Product still present in cart after removal"
        );

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart is not empty after removing product"
        );

        logger.info("===== TC027_RemoveItemFromCartTest PASSED =====");
    }
}