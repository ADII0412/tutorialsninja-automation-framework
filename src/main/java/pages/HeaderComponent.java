package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {
    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@id='top-links']//a[contains(text(),'Logout')]")
    private WebElement logoutLink;

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(css = ".btn-default.btn-lg")
    private WebElement searchButton;

    public LogoutPage logout() {
        waitForVisibility(logoutLink);
        click(logoutLink);
        return new LogoutPage(driver);
    }

    public SearchPage performSearch(String keyword) {
        type(searchInput, keyword);
        click(searchButton);
        return new SearchPage(driver);
    }
}
