package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC009_SearchUsingCategoryFilterTest extends BaseTest {

    @Test(description = "Verify search works with category and subcategory filters")
    public void verifySearchUsingCategoryFilter() {
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.CATEGORY_PRODUCT);

        searchPage.selectCategory(TestData.CATEGORY);
        searchPage.enableSubCategory();
        searchPage.clickSearch();

        logger.info("Validating filtered search results");

        Assert.assertTrue(
                searchPage.isProductDisplayed(),
                "No products displayed after applying category filter!"
        );

        Assert.assertTrue(
                searchPage.getFirstProductName().toLowerCase()
                        .contains(TestData.CATEGORY_PRODUCT.toLowerCase()),
                "Filtered results do not match expected product!"
        );
    }
}