package pages.Module;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import java.time.Duration;

public class StayProceedPaymentPage extends BasePage {

    // المشيد
    public StayProceedPaymentPage(WebDriver driver) {
        super(driver);
    }

    // محدد زر الاستمرار في الدفع بناءً على الـ HTML المرسل
    private final By proceedPaymentButton = By.id("proceedPaymentBtn");
    private final By pageLoader = By.id("page-loader"); // نkeep هذا المعرف تحسباً لوجود لودر عام بالصفحة

    /**
     * الضغط على زر Proceed to Payment للانتقال لبوابة الدفع النهائية
     */
    public void clickProceedToPayment() {
        System.out.println("Attempting to click on 'Proceed to Payment' button...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. انتظر اختفاء اللودر العام إن وجد لتفادي الـ Intercepted Exception
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
            } catch (Exception ignored) {}

            // 2. الانتظار حتى يصبح الزر ظاهراً وقابلاً للضغط تماماً
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedPaymentButton));

            // 3. عمل سكول دقيق لتوسيط الزر في الشاشة لضمان عدم حجبه من أي بنر أو هيدر
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", proceedBtn);

            // 4. النقر العادي
            proceedBtn.click();
            System.out.println("Success: 'Proceed to Payment' button clicked!");

        } catch (Exception e) {
            System.out.println("Standard click failed due to overlay or framework delay. Forcing click via JavaScript...");
            // حل الأمان النهائي في حال كان فريمورك الصفحة يحجب الزر مؤقتاً
            jsClick(proceedPaymentButton);
        }

        // انتظر ثوانٍ بسيطة حتى يبدأ المتصفح بالتحويل الفعلي لصفحة البوابة الخارجية
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }
}