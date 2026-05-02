package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "button-payment-address")
    private WebElement billingContinueBtn;

    @FindBy(id = "button-shipping-address")
    private WebElement deliveryContinueBtn;

    @FindBy(id = "button-shipping-method")
    private WebElement deliveryMethodContinueBtn;

    @FindBy(id = "button-payment-method")
    private WebElement paymentMethodContinueBtn;

    @FindBy(id = "button-confirm")
    private WebElement confirmOrderBtn;

    @FindBy(name = "comment")
    private WebElement deliveryCommentBox;

    @FindBy(name = "agree")
    private WebElement termsCheckbox;

    @FindBy(xpath = "//h1[contains(text(),'Your order has been placed')]")
    private WebElement orderSuccessMessage;

    @FindBy(xpath = "//div[contains(@class,'alert-danger')]")
    private WebElement termsWarning;

    private final By billingContinueBy = By.id("button-payment-address");
    private final By deliveryContinueBy = By.id("button-shipping-address");
    private final By deliveryMethodContinueBy = By.id("button-shipping-method");
    private final By paymentMethodContinueBy = By.id("button-payment-method");
    private final By termsBy = By.name("agree");

    private WebElement findIfPresent(By locator, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }

    // Helper method to safely click a step button when present in dynamic checkout flow.
    private boolean clickIfPresent(By locator, int timeoutSeconds) {
        WebElement element = findIfPresent(locator, timeoutSeconds);
        if (element == null) {
            return false;
        }
        waitForClickable(element);
        element.click();
        return true;
    }

    // ACTION METHODS
    public void continueBillingDetails() {
        clickIfPresent(billingContinueBy, 12);
    }

    public void continueDeliveryDetails() {
        clickIfPresent(deliveryContinueBy, 6);
    }

    public void continueDeliveryMethod(String comment) {
        WebElement button = findIfPresent(deliveryMethodContinueBy, 12);
        if (button == null) {
            return;
        }
        if (comment != null && !comment.isEmpty()) {
            type(deliveryCommentBox, comment);
        }
        waitForClickable(button);
        button.click();
    }

    public void continuePaymentMethod() {
        WebElement paymentContinue = findIfPresent(paymentMethodContinueBy, 12);
        if (paymentContinue == null) {
            return;
        }

        WebElement terms = findIfPresent(termsBy, 4);
        if (terms != null && !terms.isSelected()) {
            terms.click();
        }
        waitForClickable(paymentContinue);
        paymentContinue.click();
    }

    public void clickPaymentMethodContinueWithoutTerms() {
        clickIfPresent(paymentMethodContinueBy, 12);
    }

    public void confirmOrder() {
        // Step 6: Confirm Order
        waitForVisibility(confirmOrderBtn);
        click(confirmOrderBtn);
    }

    public String getTermsWarning() {
        waitForVisibility(termsWarning);
        return termsWarning.getText();
    }

    public void placeOrder(String comment) {
        continueBillingDetails();
        continueDeliveryDetails(); // Handles the potentially skipped Step 3
        continueDeliveryMethod(comment);
        continuePaymentMethod();
        confirmOrder();
    }

    public boolean isOrderPlaced() {
        return isDisplayed(orderSuccessMessage);
    }
}