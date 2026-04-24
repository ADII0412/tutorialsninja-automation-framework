package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    @FindBy(name = "category_id")
    private WebElement categoryDropdown;

    @FindBy(name = "sub_category")
    private WebElement subCategoryCheckbox;

    @FindBy(name = "description")
    private WebElement descriptionCheckbox;

    @FindBy(css = ".product-thumb")
    private List<WebElement> productResults;

    @FindBy(xpath = "//p[contains(text(),'There is no product')]")
    private WebElement noProductMessage;

    @FindBy(css = "button.btn-default")
    private WebElement searchButton;

    @FindBy(css = ".product-thumb h4 a")
    private WebElement productLink;

    @FindBy(css = "button[data-original-title='Add to Wish List']")
    private WebElement wishlistIcon;

    @FindBy(css = ".alert-success")
    private WebElement successMessage;

    @FindBy(xpath = "//button[@data-original-title='Compare this Product']")
    private WebElement compareButton;

    @FindBy(xpath = "//a[contains(text(),'product comparison')]")
    private WebElement compareLink;

    // Actions
    public void selectCategory(String category) {
        waitForVisibility(categoryDropdown);
        new Select(categoryDropdown).selectByVisibleText(category);
    }

    public void enableSearchInDescription() {
        click(descriptionCheckbox);
    }

    public void enableSubCategory() {
        click(subCategoryCheckbox);
    }

    public void clickSearch() {
        click(searchButton);
        wait.until(driver -> !productResults.isEmpty() || isDisplayed(noProductMessage));
    }

    public ProductPage openProduct() {
        click(productLink);
        return new ProductPage(driver);
    }

    public void addProductToWishlistFromSearch() {
        click(wishlistIcon);
    }

    public String getFirstProductName() {
        return productLink.getText();
    }

    public void addProductToCompare() {
        waitForClickable(compareButton);
        compareButton.click();
    }

    public ComparePage navigateToComparePage() {
        waitForClickable(compareLink);
        compareLink.click();
        return new ComparePage(driver);
    }

    // Validations
    public boolean isProductDisplayed() {
        return !productResults.isEmpty();
    }

    public String getNoProductMessage() {
        return getText(noProductMessage);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}