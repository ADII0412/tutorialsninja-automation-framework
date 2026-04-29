package tests.compare;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

public class TC035_AddMultipleProductsToCompareTest extends BaseTest {

    @Test(description = "Verify user can add multiple products to compare list")
    public void verifyAddMultipleProductsToCompare() {
        HomePage homePage = new HomePage(getDriver());

        List<String> expectedProducts = new ArrayList<>();

        logger.info("Adding first product: " + TestData.PRODUCT_1);
        SearchPage searchPage1 = homePage.searchProduct(TestData.PRODUCT_1);
        expectedProducts.add(TestData.PRODUCT_1);
        searchPage1.addProductToCompare();

        logger.info("Adding second product: " + TestData.PRODUCT_2);
        SearchPage searchPage2 = homePage.searchProduct(TestData.PRODUCT_2);
        expectedProducts.add(TestData.PRODUCT_2);
        searchPage2.addProductToCompare();

        logger.info("Navigating to compare page");
        ComparePage comparePage = searchPage2.navigateToComparePage();

        logger.info("Validating number of products in compare list");
        Assert.assertEquals(
                comparePage.getComparedProductsCount(),
                expectedProducts.size(),
                "Compare product count mismatch"
        );

        logger.info("Validating all products are present in compare list");

        for (String product : expectedProducts) {
            Assert.assertTrue(
                    comparePage.isProductPresent(product),
                    "Product not found in compare list: " + product
            );
        }
    }
}