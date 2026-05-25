package pages.Module;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import java.time.Duration;

public class StripeCheckoutPage extends BasePage {

    public StripeCheckoutPage(WebDriver driver) {
        super(driver);
    }

    // المحددات (Locators)
    private final By payWithoutLinkButton = By.cssSelector("button.LinkCancelPartialLoginButton");
    private final By cardNumberInput = By.id("cardNumber");
    private final By cardExpiryInput = By.id("cardExpiry");
    private final By cardCvcInput = By.id("cardCvc");
    private final By billingNameInput = By.id("billingName");

    // محدد زر الدفع النهائي التابع لـ Stripe (غالباً يحتوي على كلاس SubmitButton)
    private final By stripePayButton = By.cssSelector("button[data-testid='submit-button'], button.SubmitButton");

    /**
     * الضغط على زر Pay without Link لتخطي خيار تسجيل الدخول التلقائي وفتح نموذج البطاقة
     */
    public void clickPayWithoutLink() {
        System.out.println("Stripe page detected. Waiting for 'Pay without Link' button...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        try {
            WebElement payWithoutLinkBtn = wait.until(ExpectedConditions.elementToBeClickable(payWithoutLinkButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", payWithoutLinkBtn);

            actions.moveToElement(payWithoutLinkBtn)
                    .pause(Duration.ofMillis(500))
                    .click()
                    .perform();

            System.out.println("Success: Clicked on 'Pay without Link' button.");
        } catch (Exception e) {
            System.out.println("Standard click on Stripe failed. Forcing click via JavaScript...");
            jsClick(payWithoutLinkButton);
        }

        // انتظار بسيط جداً لحين ثبات حقول نموذج الفيزا بعد الانبثاق
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    /**
     * دالة معززة لتعبئة بيانات بطاقة Stripe التجريبية والضغط على زر الدفع
     */
    public void fillCardInformationAndSubmit(String cardNumber, String expiry, String cvc, String cardHolderName) {
        System.out.println("Waiting for Card Information inputs to become interactive...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // انتظر حتى يظهر حقل رقم البطاقة تماماً ويصبح قابلاً للكتابة
        WebElement numField = wait.until(ExpectedConditions.elementToBeClickable(cardNumberInput));

        // تعبئة البيانات خطوة بخطوة مع تنظيف الحقول مسبقاً
        numField.clear();
        numField.sendKeys(cardNumber);

        WebElement expField = driver.findElement(cardExpiryInput);
        expField.clear();
        expField.sendKeys(expiry);

        WebElement cvcField = driver.findElement(cardCvcInput);
        cvcField.clear();
        cvcField.sendKeys(cvc);

        WebElement nameField = driver.findElement(billingNameInput);
        nameField.clear();
        nameField.sendKeys(cardHolderName);
        System.out.println("Success: Test Card fields filled completely!");

        // الضغط على زر الدفع النهائي لإتمام الدورة الحركية للتست
        System.out.println("Attempting to click final Stripe Pay Button...");
        try {
            WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(stripePayButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", payBtn);
            payBtn.click();
            System.out.println("Success: Final Stripe Pay Button Clicked successfully!");
        } catch (Exception e) {
            System.out.println("Standard click on Pay Button failed. Executing via JS...");
            jsClick(stripePayButton);
        }
    }
}