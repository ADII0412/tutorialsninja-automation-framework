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

    //ACTION METHODS
    public void continueBillingDetails() {
        click(billingContinueBtn);
    }

    public void continueDeliveryDetails() {
        click(deliveryContinueBtn);
    }

    public void continueDeliveryMethod(String comment) {
        if (comment != null && !comment.isEmpty()) {
            type(deliveryCommentBox, comment);
        }
        click(deliveryMethodContinueBtn);
    }

    public void continuePaymentMethod() {
        click(termsCheckbox);
        click(paymentMethodContinueBtn);
    }

    public void confirmOrder() {
        click(confirmOrderBtn);
    }

    public String getTermsWarning() {
        return termsWarning.getText();
    }

    //COMPLETE CHECKOUT FLOW
    public void placeOrder(String comment) {
        continueBillingDetails();
        continueDeliveryDetails();
        continueDeliveryMethod(comment);
        continuePaymentMethod();
        confirmOrder();
    }

    //VALIDATION
    public boolean isOrderPlaced() {
        return isDisplayed(orderSuccessMessage);
    }
}