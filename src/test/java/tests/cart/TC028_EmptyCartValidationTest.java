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
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();
        productPage.addToCart();

        CartPage cartPage = homePage.navigateToCart();

        logger.info("Clearing all items from cart...");
        cartPage.clearCart();

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart UI does not show 'Empty' state!"
        );

        int itemCount = cartPage.getCartItemCount();
        logger.info("Current cart item count: " + itemCount);

        Assert.assertEquals(
                itemCount,
                0,
                "Cart row count should be 0 but found: " + itemCount
        );
    }
}