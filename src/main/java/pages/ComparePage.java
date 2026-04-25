package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ComparePage extends BasePage {

    public ComparePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    @FindBy(xpath = "//p[contains(text(),'You have not chosen any products')]")
    private WebElement emptyCompareMessage;

    private By productNamesLocator = By.xpath("//table[contains(@class,'table-bordered')]//td/a/strong");
    private By removeButtonsLocator = By.xpath("//a[text()='Remove']");

    // COMMON UTILS
    private List<WebElement> getProductNames() {
        return driver.findElements(productNamesLocator);
    }

    private List<WebElement> getRemoveButtons() {
        return driver.findElements(removeButtonsLocator);
    }

    // VALIDATIONS
    public int getComparedProductsCount() {
        return getProductNames().size();
    }

    public boolean isProductPresent(String productName) {
        return getProductNames().stream()
                .anyMatch(p -> p.getText().trim().equalsIgnoreCase(productName));
    }

    public boolean isCompareListEmpty() {
        return isDisplayed(emptyCompareMessage) || getProductNames().isEmpty();
    }

    // ACTIONS
    public void removeProductByName(String productName) {
        List<WebElement> names = getProductNames();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(productName)) {
                int initialSize = names.size();
                click(getRemoveButtons().get(i));
                wait.until(d -> getProductNames().size() < initialSize);
                break;
            }
        }
    }

    public void clearAllProducts() {
        int maxAttempts = 10; // Safety guard
        while (!getProductNames().isEmpty() && maxAttempts > 0) {
            int initialSize = getProductNames().size();
            click(getRemoveButtons().get(0));
            wait.until(d -> getProductNames().size() < initialSize || getProductNames().isEmpty());
            maxAttempts--;
        }
    }

    public List<String> getAllComparedProductNames() {
        return getProductNames().stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }
}