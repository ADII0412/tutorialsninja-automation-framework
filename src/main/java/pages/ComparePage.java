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

    @FindBy(xpath = "//table[contains(@class,'table-bordered')]//td/a/strong")
    private List<WebElement> comparedProductNames;

    @FindBy(xpath = "//a[text()='Remove']")
    private List<WebElement> removeButtons;

    @FindBy(xpath = "//p[contains(text(),'You have not chosen any products')]")
    private WebElement emptyCompareMessage;

    //VALIDATIONS
    public int getComparedProductsCount() {
        return comparedProductNames.size();
    }

    public boolean isProductPresent(String productName) {
        return comparedProductNames.stream()
                .anyMatch(p -> p.getText().equals(productName));
    }

    public boolean isCompareListEmpty() {
        return isDisplayed(emptyCompareMessage) || comparedProductNames.isEmpty();
    }

    //ACTIONS
    public void removeProductByName(String productName) {
        for (int i = 0; i < comparedProductNames.size(); i++) {
            if (comparedProductNames.get(i).getText().equals(productName)) {
                click(removeButtons.get(i));
                break;
            }
        }
    }

    public void clearAllProducts() {
        while (!comparedProductNames.isEmpty()) {
            click(removeButtons.get(0));
            wait.until(d -> comparedProductNames.size() >= 0);
        }
    }

    public List<String> getAllComparedProductNames() {
        return comparedProductNames.stream()
                .map(WebElement::getText)
                .toList();
    }
}