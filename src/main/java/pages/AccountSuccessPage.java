package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountSuccessPage extends BasePage {
    public AccountSuccessPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//div[@id='content']/h1")
    private WebElement successHeading;

    public String getSuccessMessage() {
        waitForVisibility(successHeading);
        return successHeading.getText();
    }
}