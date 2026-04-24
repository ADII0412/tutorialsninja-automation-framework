package tests.product;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC014_ProductImageThumbnailClickableTest extends BaseTest {

    @Test(description = "Verify product image thumbnails are clickable and enlarge the image")
    public void verifyImageThumbnailsAreClickable() {

        logger.info("Starting TC014: Product Image Thumbnail Click Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        logger.info("Validating thumbnails are displayed");

        Assert.assertTrue(
                productPage.areProductImagesDisplayed(),
                "Product thumbnails are not displayed!"
        );

        logger.info("Clicking first thumbnail");

        productPage.clickFirstImageThumbnail();

        logger.info("Validating enlarged image display");

        Assert.assertTrue(
                productPage.isEnlargedImageDisplayed(),
                "Enlarged product image was not displayed after clicking thumbnail!"
        );

        logger.info("TC014 Passed");
    }
}