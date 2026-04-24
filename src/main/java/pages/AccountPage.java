package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage {
    public AccountPage(WebDriver driver){
        super(driver);
    }

    @FindBy(linkText = "Edit your account information")
    private WebElement editAccountInfoLink;

    public boolean isEditAccountInfoDisplayed() {
        waitForVisibility(editAccountInfoLink);
        return editAccountInfoLink.isDisplayed();
    }
}