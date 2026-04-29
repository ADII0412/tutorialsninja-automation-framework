package tests.product;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC016_RelatedProductsDisplayTest extends BaseTest {
    @Test(description = "Verify related products are displayed on product page")
    public void verifyRelatedProductsDisplayCorrectly() {

        logger.info("Starting TC016: Related Products Display Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        logger.info("Validating related products section");

        Assert.assertTrue(
                productPage.areRelatedProductsDisplayed(),
                "Related products are not displayed on product page!"
        );
    }
}