package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    @FindBy(xpath = "//p[contains(text(),'Your shopping cart is empty')]")
    private WebElement emptyCartMessage;

    @FindBy(xpath = "//a[contains(@class,'btn-primary') and contains(text(),'Checkout')]")
    private WebElement checkoutButton;

    private By cartRows = By.xpath("//div[@class='table-responsive']//tbody//tr");
    private By productName = By.xpath(".//td[2]");
    private By quantityInput = By.xpath(".//input[contains(@name,'quantity')]");
    private By updateBtn = By.xpath(".//button[@data-original-title='Update']");
    private By removeBtn = By.xpath(".//button[@data-original-title='Remove']");

    // COMMON UTIL
    private List<WebElement> getRows() {
        return driver.findElements(cartRows);
    }

    private WebElement getRowByProductName(String name) {
        return getRows().stream()
                .filter(row -> row.findElement(productName)
                        .getText()
                        .trim()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    // VALIDATIONS
    public boolean isProductPresentInCart(String name) {
        WebElement row = getRowByProductName(name);
        return row != null && row.isDisplayed();
    }

    public int getCartItemCount() {
        return getRows().size();
    }

    public boolean isCartEmpty() {
        // Now safe because getRows() no longer throws TimeoutException
        return isDisplayed(emptyCartMessage) || getRows().isEmpty();
    }

    public String getProductQuantity(String name) {
        WebElement row = getRowByProductName(name);
        if (row == null) return null;

        return row.findElement(quantityInput).getAttribute("value");
    }

    // ACTIONS
    public void updateProductQuantity(String name, String quantity) {
        WebElement row = getRowByProductName(name);
        if (row == null) return;

        WebElement qtyInputField = row.findElement(quantityInput);
        type(qtyInputField, quantity);
        click(row.findElement(updateBtn));

        wait.until(d -> {
            try {
                return qtyInputField.getAttribute("value").equals(quantity);
            } catch (StaleElementReferenceException e) {
                // If element goes stale during update, find it again via the row
                return getRowByProductName(name).findElement(quantityInput).getAttribute("value").equals(quantity);
            }
        });
    }

    public void removeProductByName(String name) {
        WebElement row = getRowByProductName(name);
        if (row == null) return;

        int initialSize = getRows().size();
        click(row.findElement(removeBtn));

        wait.until(d -> getRows().size() < initialSize);
    }

    public CheckoutPage proceedToCheckout() {
        waitForClickable(checkoutButton);
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

    public boolean canProceedToCheckout() {
        try {
            return checkoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clearCart() {
        int maxAttempts = 10;
        while (maxAttempts > 0) {
            List<WebElement> rows = getRows();
            if (rows.isEmpty()) break;
            int initialSize = rows.size();
            click(rows.get(0).findElement(removeBtn));
            wait.until(d -> getRows().size() < initialSize || getRows().isEmpty());
            maxAttempts--;
        }
    }
}