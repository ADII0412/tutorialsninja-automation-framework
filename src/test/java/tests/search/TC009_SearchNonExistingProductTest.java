package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC009_SearchNonExistingProductTest extends BaseTest {

    @Test(description = "Verify no results message is shown for non-existing product search")
    public void verifySearchWithNonExistingProductShowsNoResult() {

        logger.info("Starting TC009: Search Non-Existing Product Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.INVALID_PRODUCT);

        logger.info("Validating no product message");

        String message = searchPage.getNoProductMessage();

        Assert.assertTrue(
                message.contains("no product"),
                "No product message not displayed!"
        );

        logger.info("TC009 Passed");
    }
}