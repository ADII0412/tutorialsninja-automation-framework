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
        // Redundant start/end logs removed (handled by Listener)

        HomePage homePage = new HomePage(getDriver());
        SearchPage searchPage = homePage.searchProduct(TestData.PRODUCT_NAME);
        ProductPage productPage = searchPage.openProduct();

        logger.info("Submitting product review for: " + TestData.PRODUCT_NAME);

        productPage.submitReview(
                TestData.REVIEW_NAME,
                TestData.REVIEW_TEXT
        );

        String message = productPage.getReviewSuccessMessage();

        Assert.assertTrue(
                message.contains("Thank you"),
                "Expected review success message but got: " + message
        );
    }
}