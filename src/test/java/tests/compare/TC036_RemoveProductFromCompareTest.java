package tests.compare;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC036_RemoveProductFromCompareTest extends BaseTest {

    @Test(description = "Verify user can remove product from compare list")
    public void TC036_verifyRemoveProductFromCompare() {

        logger.info("===== Starting TC036_RemoveProductFromCompareTest =====");

        HomePage homePage = new HomePage(getDriver());

        logger.info("Adding product to compare: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        searchPage.addProductToCompare();

        logger.info("Navigating to compare page");
        ComparePage comparePage = searchPage.navigateToComparePage();

        Assert.assertTrue(
                comparePage.isProductPresent(TestData.PRODUCT_NAME),
                "Product not present in compare list before removal"
        );

        logger.info("Removing product from compare list");
        comparePage.removeProductByName(TestData.PRODUCT_NAME);

        logger.info("Validating product is removed from compare list");

        Assert.assertFalse(
                comparePage.isProductPresent(TestData.PRODUCT_NAME),
                "Product still present in compare list after removal"
        );

        logger.info("Validating compare list is empty");

        Assert.assertTrue(
                comparePage.isCompareListEmpty(),
                "Compare list is not empty after removal"
        );

        logger.info("===== TC036_RemoveProductFromCompareTest PASSED =====");
    }
}