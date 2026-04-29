package pages;

import base.BasePage;
import org.openqa.selenium.By;
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

    private By productResultsLocator = By.cssSelector(".product-thumb");

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

    // ACTIONS
    public void selectCategory(String category) {
        waitForVisibility(categoryDropdown);
        new Select(categoryDropdown).selectByVisibleText(category);
    }

    public void enableSearchInDescription() {
        if (!descriptionCheckbox.isSelected()) {
            click(descriptionCheckbox);
        }
    }

    public void enableSubCategory() {
        if (!subCategoryCheckbox.isSelected()) {
            click(subCategoryCheckbox);
        }
    }

    public void clickSearch() {
        click(searchButton);
        wait.until(d -> {
            boolean resultsFound = !d.findElements(productResultsLocator).isEmpty();
            boolean noResultsMsgFound = d.findElements(By.xpath("//p[contains(text(),'There is no product')]")).size() > 0;
            return resultsFound || noResultsMsgFound;
        });
    }

    public ProductPage openProduct() {
        waitForVisibility(productLink);
        click(productLink);
        return new ProductPage(driver);
    }

    public void addProductToWishlistFromSearch() {
        click(wishlistIcon);
    }

    public String getFirstProductName() {
        waitForVisibility(productLink);
        return productLink.getText();
    }

    public void addProductToCompare() {
        waitForClickable(compareButton);
        click(compareButton);
    }

    public ComparePage navigateToComparePage() {
        waitForClickable(compareLink);
        click(compareLink);
        return new ComparePage(driver);
    }

    // VALIDATIONS
    public boolean isProductDisplayed() {
        return !driver.findElements(productResultsLocator).isEmpty();
    }

    public String getNoProductMessage() {
        waitForVisibility(noProductMessage);
        return getText(noProductMessage);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }
}