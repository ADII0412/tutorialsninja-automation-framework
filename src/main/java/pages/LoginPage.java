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

    @FindBy(xpath = "//div[contains(@class,'alert-danger')]")
    private WebElement warningMessage;

    @FindBy(linkText = "Forgotten Password")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//div[@class='list-group']//a[contains(text(),'Wish List')]")
    private WebElement wishListLink;

    // ACTIONS
    public AccountPage login(String email, String password) {
        enterCredentials(email, password);
        click(loginButton);
        return new AccountPage(driver);
    }

    public LoginPage loginWithInvalidCredentials(String email, String password) {
        enterCredentials(email, password);
        click(loginButton);
        return this;
    }

    private void enterCredentials(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
    }

    public ForgotPasswordPage navigateToForgotPassword() {
        click(forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }

    public WishlistPage navigateToWishlist() {
        click(wishListLink);
        return new WishlistPage(driver);
    }

    // VALIDATION
    public String getWarningMessage() {
        waitForVisibility(warningMessage);
        return getText(warningMessage);
    }
}