package tests.search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.TestData;

public class TC009_SearchNonExistingProductTest extends BaseTest {

    @Test(groups = {"regression", "negative"}, description = "Verify no results message is shown for non-existing product search")
    public void verifySearchWithNonExistingProductShowsNoResult() {
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.INVALID_PRODUCT);

        logger.info("Validating no product message");

        String message = searchPage.getNoProductMessage();

        Assert.assertTrue(
                message.contains("no product"),
                "No product message not displayed!"
        );
    }
}
