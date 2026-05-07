package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestData;

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

    @FindBy(id = "input-payment-firstname")
    private WebElement firstNameField;

    @FindBy(id = "input-payment-lastname")
    private WebElement lastNameField;

    @FindBy(id = "input-payment-address-1")
    private WebElement addressField;

    @FindBy(id = "input-payment-city")
    private WebElement cityField;

    @FindBy(id = "input-payment-postcode")
    private WebElement postcodeField;

    @FindBy(id = "input-payment-country")
    private WebElement countryDropdown;

    @FindBy(id = "input-payment-zone")
    private WebElement regionDropdown;

    @FindBy(css = "input[name='shipping_method']")
    private WebElement shippingMethodRadio;

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
        fillBillingAddress();
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
        selectShippingMethod();
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

    public void fillBillingAddress() {
        if (isDisplayed(firstNameField)) {
            type(firstNameField, TestData.BILLING_FIRSTNAME);
            type(lastNameField, TestData.BILLING_LASTNAME);
            type(addressField, TestData.BILLING_ADDRESS);
            type(cityField, TestData.BILLING_CITY);
            type(postcodeField, TestData.BILLING_POSTCODE);

            new Select(countryDropdown).selectByVisibleText(TestData.BILLING_COUNTRY);
            wait.until(d -> {
                try {
                    return new Select(regionDropdown).getOptions().stream()
                            .anyMatch(option -> option.getText().trim().equals(TestData.BILLING_REGION));
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
            new Select(regionDropdown).selectByVisibleText(TestData.BILLING_REGION);
        }

        clickIfPresent(billingContinueBy, 12);
        waitForVisibility(deliveryContinueBtn);
    }

    public void selectShippingMethod() {
        waitForClickable(shippingMethodRadio);
        if (!shippingMethodRadio.isSelected()) {
            shippingMethodRadio.click();
        }
        click(deliveryMethodContinueBtn);
    }

    public void clickPaymentMethodContinueWithoutTerms() {
        clickIfPresent(paymentMethodContinueBy, 12);
    }

    public void confirmOrder() {
        waitForVisibility(confirmOrderBtn);
        click(confirmOrderBtn);
    }

    public String getTermsWarning() {
        waitForVisibility(termsWarning);
        return termsWarning.getText();
    }

    public void placeOrder(String comment) {
        fillBillingAddress();
        waitForVisibility(deliveryContinueBtn);
        click(deliveryContinueBtn);

        waitForVisibility(deliveryMethodContinueBtn);
        if (comment != null && !comment.isEmpty()) {
            type(deliveryCommentBox, comment);
        }
        selectShippingMethod();

        waitForVisibility(termsCheckbox);
        click(termsCheckbox);
        click(paymentMethodContinueBtn);

        waitForVisibility(confirmOrderBtn);
        click(confirmOrderBtn);
    }

    public boolean isOrderPlaced() {
        return isDisplayed(orderSuccessMessage);
    }
}
