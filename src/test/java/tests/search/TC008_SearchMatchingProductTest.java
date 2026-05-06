package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC008_SearchMatchingProductTest extends BaseTest {

    @Test(description = "Verify search results match the search keyword")
    public void verifySearchReturnsMatchingProduct() {
        HomePage homePage = new HomePage(getDriver());

        logger.info("Searching for product: " + TestData.VALID_PRODUCT);
        SearchPage searchPage = homePage.searchProduct(TestData.VALID_PRODUCT);

        Assert.assertTrue(
                searchPage.isProductDisplayed(),
                "Search results page is empty for keyword: " + TestData.VALID_PRODUCT
        );

        String firstProductName = searchPage.getFirstProductName().toLowerCase();
        String searchKeyword = TestData.VALID_PRODUCT.toLowerCase();

        logger.info("Validating first search result: " + firstProductName);

        Assert.assertTrue(
                firstProductName.contains(searchKeyword),
                "Search result mismatch! Expected keyword '" + searchKeyword + "' but found '" + firstProductName + "'"
        );
    }
}