package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC010_SearchWithPartialKeywordTest extends BaseTest {

    @Test(description = "Verify search works with partial keyword")
    public void verifySearchWithPartialKeywordWorks() {

        logger.info("Starting TC010: Partial Keyword Search Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PARTIAL_PRODUCT);

        logger.info("Validating partial keyword search results");

        Assert.assertTrue(
                searchPage.getFirstProductName().toLowerCase().contains(TestData.PARTIAL_PRODUCT.toLowerCase()),
                "Search results do not match partial keyword!"
        );

        logger.info("TC010 Passed");
    }
}