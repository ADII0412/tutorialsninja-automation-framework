package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "input-email")
    private WebElement emailField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[contains(text(),'Warning')]")
    private WebElement warningMessage;

    @FindBy(linkText = "Forgotten Password")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//div[@class='list-group']//a[contains(text(),'Wish List')]")
    private WebElement wishListLink;

    // Actions
    public AccountPage login(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
        return new AccountPage(driver);
    }

    public void loginWithInvalidCredentials(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
    }

    public ForgotPasswordPage navigateToForgotPassword() {
        click(forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }

    public WishlistPage navigateToWishlist() {
        click(wishListLink);
        return new WishlistPage(driver);
    }

    // Validation
    public String getWarningMessage() {
        return getText(warningMessage);
    }
}