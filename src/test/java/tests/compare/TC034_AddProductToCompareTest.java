package tests.compare;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

public class TC034_AddProductToCompareTest extends BaseTest {

    @Test(description = "Verify user can add a product to compare list")
    public void verifyAddProductToCompare() {
        HomePage homePage = new HomePage(getDriver());

        logger.info("Searching product: " + TestData.PRODUCT_NAME);
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);

        logger.info("Adding product to compare");
        searchPage.addProductToCompare();

        String successMsg = searchPage.getSuccessMessage();

        Assert.assertTrue(
                successMsg.toLowerCase().contains("product comparison"),
                "Product not added to compare successfully"
        );

        logger.info("Navigating to compare page");
        ComparePage comparePage = searchPage.navigateToComparePage();

        logger.info("Validating product is present in compare list");

        Assert.assertTrue(
                comparePage.isProductPresent(TestData.PRODUCT_NAME),
                "Product not found in compare list"
        );
    }
}