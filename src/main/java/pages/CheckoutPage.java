package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "button-payment-address")
    private WebElement billingContinueBtn;

    @FindBy(id = "button-shipping-address")
    private WebElement deliveryContinueBtn;

    @FindBy(name = "comment")
    private WebElement deliveryCommentBox;

    @FindBy(id = "button-shipping-method")
    private WebElement deliveryMethodContinueBtn;

    @FindBy(name = "agree")
    private WebElement termsCheckbox;

    @FindBy(id = "button-payment-method")
    private WebElement paymentMethodContinueBtn;

    @FindBy(id = "button-confirm")
    private WebElement confirmOrderBtn;

    @FindBy(xpath = "//h1[contains(text(),'Your order has been placed')]")
    private WebElement orderSuccessMessage;

    @FindBy(xpath = "//div[contains(@class,'alert-danger')]")
    private WebElement termsWarning;

    // ACTION METHODS
    private void waitForStepToLoad(WebElement stepButton) {
        // First wait for the element to be present/visible
        waitForVisibility(stepButton);
        // Then add a small buffer for the accordion slide animation
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        waitForClickable(stepButton);
    }

    public void continueBillingDetails() {
        waitForStepToLoad(billingContinueBtn);
        click(billingContinueBtn);
    }

    public void continueDeliveryDetails() {
        waitForStepToLoad(deliveryContinueBtn);
        click(deliveryContinueBtn);
    }

    public void continueDeliveryMethod(String comment) {
        waitForStepToLoad(deliveryMethodContinueBtn);
        if (comment != null && !comment.isEmpty()) {
            waitForVisibility(deliveryCommentBox);
            type(deliveryCommentBox, comment);
        }
        click(deliveryMethodContinueBtn);
    }

    // This method is for the HAPPY PATH (Accepting terms)
    public void continuePaymentMethod() {
        waitForStepToLoad(paymentMethodContinueBtn);
        waitForClickable(termsCheckbox);
        click(termsCheckbox);
        click(paymentMethodContinueBtn);
    }

    // NEW: Specifically for TC033 (Negative Path - No Terms)
    public void clickPaymentMethodContinueWithoutTerms() {
        waitForStepToLoad(paymentMethodContinueBtn);
        click(paymentMethodContinueBtn);
    }

    public void confirmOrder() {
        waitForClickable(confirmOrderBtn);
        click(confirmOrderBtn);
    }

    public String getTermsWarning() {
        waitForVisibility(termsWarning);
        return termsWarning.getText();
    }

    public void placeOrder(String comment) {
        continueBillingDetails();
        continueDeliveryDetails();
        continueDeliveryMethod(comment);
        continuePaymentMethod();
        confirmOrder();
    }

    // VALIDATION
    public boolean isOrderPlaced() {
        return isDisplayed(orderSuccessMessage);
    }
}