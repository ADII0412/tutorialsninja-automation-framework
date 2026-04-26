package tests.product;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC014_ProductImageThumbnailClickableTest extends BaseTest {

    @Test(description = "Verify product image thumbnails are clickable and trigger a lightbox display")
    public void verifyImageThumbnailsAreClickable() {
        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        Assert.assertTrue(
                productPage.areProductImagesDisplayed(),
                "Product thumbnails are not present on the page!"
        );

        logger.info("Clicking the first thumbnail to trigger lightbox animation");
        productPage.clickFirstImageThumbnail();

        Assert.assertTrue(
                productPage.isEnlargedImageDisplayed(),
                "Lightbox/Enlarged image failed to appear after clicking the thumbnail!"
        );
    }
}