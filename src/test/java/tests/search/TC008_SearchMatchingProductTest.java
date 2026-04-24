package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC008_SearchMatchingProductTest extends BaseTest {

    @Test(description = "Verify search returns results for a valid product")
    public void verifySearchReturnsMatchingProduct() {

        logger.info("Starting TC008: Search Matching Product Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.VALID_PRODUCT);

        logger.info("Validating product search results");

        Assert.assertTrue(
                searchPage.isProductDisplayed(),
                "No products displayed for valid search!"
        );

        logger.info("TC008 Passed");
    }
}