package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Locators
    @FindBy(css = "a[title='My Account']")
    private WebElement myAccountDropdown;

    @FindBy(linkText = "Login")
    private WebElement loginOption;

    @FindBy(linkText = "Register")
    private WebElement registerOption;

    @FindBy(name = "search")
    private WebElement searchBox;

    @FindBy(css = "button.btn-default")
    private WebElement searchButton;

    @FindBy(id = "wishlist-total")
    private WebElement wishlistLink;

    @FindBy(linkText = "Logout")
    private WebElement logoutOption;

    @FindBy(id = "cart-total")
    private WebElement cartButton;

    @FindBy(linkText = "View Cart")
    private WebElement viewCartOption;

    @FindBy(xpath = "//a[@title='Shopping Cart']")
    private WebElement shoppingCartHeaderLink;

    @FindBy(xpath = "//a[@title='Checkout']")
    private WebElement checkoutHeaderLink;

    // Reusable method
    private void openMyAccountDropdown() {
        click(myAccountDropdown);
    }

    // Navigation Methods

    public LoginPage navigateToLogin() {
        openMyAccountDropdown();
        click(loginOption);
        return new LoginPage(driver);
    }

    public RegisterPage navigateToRegister() {
        openMyAccountDropdown();
        click(registerOption);
        return new RegisterPage(driver);
    }

    public LogoutPage logout() {
        openMyAccountDropdown();
        click(logoutOption);
        return new LogoutPage(driver);
    }

    public WishlistPage navigateToWishlist() {
        click(wishlistLink);
        return new WishlistPage(driver);
    }

    public CartPage navigateToCart() {
        waitForVisibility(shoppingCartHeaderLink);
        click(shoppingCartHeaderLink);
        return new CartPage(driver);
    }

    public CheckoutPage navigateToCheckout() {
        waitForVisibility(checkoutHeaderLink);
        click(checkoutHeaderLink);
        return new CheckoutPage(driver);
    }

    public SearchPage searchProduct(String keyword) {
        type(searchBox, keyword);
        click(searchButton);
        return new SearchPage(driver);
    }
}