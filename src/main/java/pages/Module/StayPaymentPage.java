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

public class StayPaymentPage extends BasePage {

    // المشيد
    public StayPaymentPage(WebDriver driver) {
        super(driver);
    }

    // 1. المحددات المستوحاة من كودك الفعال والـ HTML الفعلي
    // تم استخدام الـ index [2] أو المعرف المباشر بناءً على طريقة بناء الفريمورك للشروط
    private final By agreeTermsAndConditions = By.xpath("(//div[contains(@class,'checkbox-custom')])[2]");
    private final By confirmBookingButton = By.xpath("//span[contains(text(),'Confirm Booking')]");

    // 2. العمليات (Actions)

    /**
     * اختيار طريقة الدفع بناءً على اسم الطريقة (مثال: "Pay Later", "PayPal")
     */
    public void selectPaymentMethod(String methodName) {
        waitForLoaderToDisappear();

        String paymentValue = "";
        switch (methodName.toLowerCase().trim()) {
            case "pay later":
                paymentValue = "6";
                break;
            case "paypal":
            case "digital wallet":
                paymentValue = "3";
                break;
            case "credit card":
            case "stripe":
                paymentValue = "7";
                break;
            case "wallet balance":
            case "internal wallet":
                paymentValue = "10";
                break;
            default:
                throw new IllegalArgumentException("طريقة الدفع غير مدعومة: " + methodName);
        }

        By paymentRadioLabel = By.cssSelector("label[for='payment_" + paymentValue + "']");
        waitForVisibility(paymentRadioLabel);
        jsClick(paymentRadioLabel);
        System.out.println("Success: Selected payment method: " + methodName);
    }

    /**
     * الضغط على الشروط والأحكام وتأكيد الحجز باستخدام تكتيك معزز الاستقرار لـ Pay Later
     */
    public void acceptTermsAndConfirmBooking() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        // مهلة صغيرة جداً (500ms) للتأكد من استقرار الـ DOM بعد اختيار طريقة الدفع وتحديث جافا سكريبت للموقع
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        System.out.println("Step 1: Waiting for Terms & Conditions checkbox...");
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(agreeTermsAndConditions));

        // تمرير العنصر لمنتصف الشاشة
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        // النقر الذكي
        actions.moveToElement(checkbox)
                .pause(Duration.ofMillis(500))
                .click()
                .perform();
        System.out.println("Success: Terms & Conditions clicked.");

        System.out.println("Step 2: Waiting for Confirm Booking button...");
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmBookingButton));

        // التمرير لزر التأكيد لضمان عدم وجود أي طبقة تحجبه
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", confirmButton);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}

        // تنفيذ النقر النهائي
        try {
            actions.moveToElement(confirmButton)
                    .pause(Duration.ofMillis(500))
                    .click()
                    .perform();
            System.out.println("Success: Confirm Booking button clicked via Actions!");
        } catch (Exception e) {
            System.out.println("Actions click failed on confirm button. Forcing via JavaScript...");
            jsClick(confirmBookingButton);
        }
    }
}