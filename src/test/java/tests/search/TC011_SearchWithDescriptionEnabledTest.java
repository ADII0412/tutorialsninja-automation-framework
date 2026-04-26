package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC011_SearchWithDescriptionEnabledTest extends BaseTest {
    @Test(description = "Verify search works when description option is enabled")
    public void verifySearchWithProductDescriptionEnabled() {
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.DESCRIPTION_PRODUCT);

        searchPage.enableSearchInDescription();
        searchPage.clickSearch();

        logger.info("Validating search results using description");

        Assert.assertTrue(
                searchPage.isProductDisplayed(),
                "No products displayed when searching with description enabled!"
        );

        Assert.assertTrue(
                searchPage.getFirstProductName().toLowerCase()
                        .contains(TestData.DESCRIPTION_PRODUCT.toLowerCase()),
                "Search results do not match description keyword!"
        );
    }
}