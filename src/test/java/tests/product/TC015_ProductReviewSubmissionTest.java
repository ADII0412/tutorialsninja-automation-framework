package tests.product;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;
import utils.TestData;

public class TC015_ProductReviewSubmissionTest extends BaseTest {

    @Test(description = "Verify user can submit a product review successfully")
    public void verifyProductReviewSubmission() {

        logger.info("Starting TC015: Product Review Submission Test");

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        logger.info("Submitting product review");

        productPage.submitReview(
                TestData.REVIEW_NAME,
                TestData.REVIEW_TEXT
        );

        logger.info("Validating review submission success message");

        String message = productPage.getReviewSuccessMessage();

        Assert.assertTrue(
                message.contains("Thank you"),
                "Review submission success message not displayed!"
        );

        logger.info("TC015 Passed");
    }
}