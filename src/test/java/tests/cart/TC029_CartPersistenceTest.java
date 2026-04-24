package tests.cart;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC029_CartPersistenceTest extends BaseTest {

    @Test(description = "Verify cart retains items after page refresh")
    public void TC029_verifyCartPersistence() {

        logger.info("===== Starting TC029_CartPersistenceTest =====");

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

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product not present in cart before refresh"
        );

        logger.info("Refreshing the page");
        getDriver().navigate().refresh();
        cartPage = new CartPage(getDriver());

        Assert.assertTrue(
                cartPage.isProductPresentInCart(productName),
                "Product not present in cart after refresh"
        );

        logger.info("===== TC029_CartPersistenceTest PASSED =====");
    }
}