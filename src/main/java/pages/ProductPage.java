package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    //LOCATORS
    @FindBy(css = "#content h1")
    private WebElement productTitle;

    @FindBy(css = ".thumbnails img")
    private List<WebElement> productImages;

    @FindBy(css = ".list-unstyled h2")
    private WebElement productPrice;

    @FindBy(id = "button-cart")
    private WebElement addToCartButton;

    @FindBy(xpath = "//button[@class='btn btn-inverse btn-block btn-lg dropdown-toggle']")
    private WebElement cartButton;

    @FindBy(xpath = "//strong[normalize-space()='View Cart']")
    private WebElement viewCartButton;

    @FindBy(css = "button[data-original-title='Add to Wish List']")
    private WebElement addToWishlistButton;

    @FindBy(css = ".alert-success")
    private WebElement successMessage;

    @FindBy(css = "a[href='#tab-review']")
    private WebElement reviewButton;

    @FindBy(id = "input-name")
    private WebElement reviewNameField;

    @FindBy(id = "input-review")
    private WebElement reviewTextField;

    @FindBy(css = "input[name='rating'][value='5']")
    private WebElement fiveStarRating;

    @FindBy(id = "button-review")
    private WebElement submitReviewButton;

    @FindBy(css = ".alert-success")
    private WebElement reviewSuccessMessage;

    // Related Products
    @FindBy(css = ".col-xs-6")
    private List<WebElement> relatedProducts;

    // Enlarged Image (popup)
    @FindBy(css = ".mfp-img")
    private WebElement enlargedImage;


    //VALIDATIONS
    public String getProductTitle() {
        return getText(productTitle);
    }

    public boolean isPriceDisplayed() {
        return isDisplayed(productPrice);
    }

    public boolean isAddToCartButtonDisplayed() {
        return isDisplayed(addToCartButton);
    }

    public boolean areProductImagesDisplayed() {
        return !productImages.isEmpty();
    }

    public boolean areRelatedProductsDisplayed() {
        return !relatedProducts.isEmpty();
    }

    public boolean isEnlargedImageDisplayed() {
        return isDisplayed(enlargedImage);
    }

    // ACTIONS
    public void clickFirstImageThumbnail() {
        if (!productImages.isEmpty()) {
            click(productImages.get(0));
        }
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void addToWishlist() {
        click(addToWishlistButton);
    }

    public CartPage clickCartButton() {
        waitForVisibility(cartButton);
        click(cartButton);
        click(viewCartButton);
        return new CartPage(driver);
    }

    //REVIEW
    public void submitReview(String name, String review) {
        waitForVisibility(reviewButton);
        click(reviewButton);
        waitForVisibility(reviewNameField);
        type(reviewNameField, name);
        type(reviewTextField, review);
        click(fiveStarRating);
        click(submitReviewButton);
    }

    public String getReviewSuccessMessage() {
        return getText(reviewSuccessMessage);
    }

    //COMMON
    public String getSuccessMessage() {
        return getText(successMessage);
    }
}