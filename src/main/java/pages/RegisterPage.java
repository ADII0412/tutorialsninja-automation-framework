package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver){
        super(driver);
    }

    @FindBy(id = "input-firstname")
    private WebElement firstNameField;

    @FindBy(id = "input-lastname")
    private WebElement lastNameField;

    @FindBy(id = "input-email")
    private WebElement emailField;

    @FindBy(id = "input-telephone")
    private WebElement telephoneField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(id = "input-confirm")
    private WebElement confirmPasswordField;

    @FindBy(name = "agree")
    private WebElement privacyPolicyCheckbox;

    @FindBy(xpath = "//div[contains(text(),'You must agree')]")
    private WebElement privacyPolicyWarning;

    @FindBy(xpath = "//div[contains(text(),'already registered')]")
    private WebElement duplicateEmailWarning;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement continueButton;

    private void fillRegistrationForm(String firstName, String lastName, String email, String telephone, String password) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(telephoneField, telephone);
        type(passwordField, password);
        type(confirmPasswordField, password);
    }

    public AccountSuccessPage registerAccount(String firstName, String lastName, String email, String telephone, String password) {
        fillRegistrationForm(firstName, lastName, email, telephone, password);
        click(privacyPolicyCheckbox);
        click(continueButton);
        return new AccountSuccessPage(driver);
    }

    public void submitWithoutPrivacyPolicy(String firstName, String lastName, String email, String telephone, String password) {
        fillRegistrationForm(firstName, lastName, email, telephone, password);
        click(continueButton);
    }

    public String getDuplicateEmailWarning() {
        return getText(duplicateEmailWarning);
    }

    public String getPrivacyPolicyWarning() {
        return getText(privacyPolicyWarning);
    }
}