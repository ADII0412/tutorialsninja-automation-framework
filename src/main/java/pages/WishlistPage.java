package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WishlistPage extends BasePage {

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    // LOCATORS
    private By wishlistRows = By.xpath("//table[@class='table table-bordered table-hover']//tbody/tr");
    private By productName = By.xpath(".//td[2]");
    private By removeBtn = By.xpath(".//a[@class='btn btn-danger']");
    private By addToCartBtn = By.xpath(".//button[@data-original-title='Add to Cart']");
    @FindBy (xpath = "//div[@class='list-group']//a[contains(text(), 'Logout')]")
    private WebElement logoutLink;

    // ================= COMMON UTIL =================

    private List<WebElement> getRows() {
        return driver.findElements(wishlistRows);
    }

    private WebElement getRowByProductName(String name) {
        return getRows().stream()
                .filter(row -> row.findElement(productName).getText().equals(name))
                .findFirst()
                .orElse(null);
    }

    // ================= VALIDATIONS =================

    public int getWishlistItemCount() {
        return getRows().size();
    }

    public boolean isWishlistEmpty() {
        return getRows().isEmpty();
    }

    public boolean isProductPresent(String name) {
        return getRowByProductName(name) != null;
    }

    // ================= ACTIONS =================

    public void removeFirstProduct() {
        List<WebElement> rows = getRows();
        if (rows.isEmpty()) return;
        int initialSize = rows.size();
        click(rows.get(0).findElement(removeBtn));
        wait.until(d -> getRows().size() < initialSize);
    }

    public void removeProductByName(String name) {
        WebElement row = getRowByProductName(name);
        if (row == null) return;
        int initialSize = getRows().size();
        click(row.findElement(removeBtn));
        wait.until(d -> getRows().size() < initialSize);
    }

    public void moveFirstProductToCart() {
        List<WebElement> rows = getRows();
        if (rows.isEmpty()) return;
        click(rows.get(0).findElement(addToCartBtn));
    }

    public void moveProductToCartByName(String name) {
        WebElement row = getRowByProductName(name);
        if (row == null) return;
        click(row.findElement(addToCartBtn));
    }

    public LogoutPage logout() {
        waitForVisibility(logoutLink);
        click(logoutLink);
        return new LogoutPage(driver);
    }

    public void clearWishlist() {
        while (true) {
            List<WebElement> rows = getRows();
            if (rows.isEmpty()) break;
            int initialSize = rows.size();
            click(rows.get(0).findElement(removeBtn));
            wait.until(d ->
                    getRows().size() < initialSize || getRows().isEmpty()
            );
        }
    }
}