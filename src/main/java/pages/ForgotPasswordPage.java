package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordPage extends BasePage {

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1[text()='Forgot Your Password?']")
    private WebElement pageHeading;

    public boolean isForgotPasswordPageDisplayed() {
        return isDisplayed(pageHeading);
    }
}