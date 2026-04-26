package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

public class TC011_EmptySearchTest extends BaseTest {
    @Test(description = "Verify system handles blank search input gracefully")
    public void verifyEmptySearch() {
        HomePage homePage = new HomePage(getDriver());

        logger.info("Performing search with an empty string");

        SearchPage searchPage = homePage.searchProduct("");

        String resultsText = searchPage.getNoProductMessage().toLowerCase();

        Assert.assertTrue(
                resultsText.contains("there is no product") || resultsText.contains("0"),
                "System did not show 'No Results' message for empty search! Found: " + resultsText
        );
    }
}
