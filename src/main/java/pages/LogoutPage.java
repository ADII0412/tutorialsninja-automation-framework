package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends BasePage {
    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Continue")
    private WebElement continueButton;

    public HomePage clickContinue() {
        waitForClickable(continueButton);
        continueButton.click();
        return new HomePage(driver);
    }
}