package pages.Module;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import java.time.Duration;

public class StayInvoicePage extends BasePage {

    // المشيد
    public StayInvoicePage(WebDriver driver) {
        super(driver);
    }

    // المحددات
    private final By paymentGatewayDropdown = By.id("payment_gateway");
    private final By pageLoader = By.id("page-loader"); // محدد اللودر المتسبب في الأزمة
    private final By makePaymentButton = By.id("makePaymentBtn");

    /**
     * تغيير بوابة الدفع إلى Stripe مع التعامل مع شاشة التحميل الحاقنة
     */
    public void selectPaymentGateway(String gatewayValue) {
        System.out.println("Waiting for Invoice page to stabilize...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. الانتظار الحاسم: انتظر حتى يختفي اللودر تماماً من الشاشة ولا يحجب أي نقرة
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));
            System.out.println("Page loader disappeared.");
        } catch (Exception e) {
            System.out.println("Page loader timeout or already hidden. Proceeding...");
        }

        // 2. انتظر ظهور القائمة المنسدلة نفسها وتأكد أنها قابلة للتفاعل
        wait.until(ExpectedConditions.elementToBeClickable(paymentGatewayDropdown));
        WebElement dropdownElement = driver.findElement(paymentGatewayDropdown);

        try {
            // محاولة أولى: الاختيار العادي عن طريق كلاس Select
            Select gatewaySelect = new Select(dropdownElement);
            gatewaySelect.selectByValue(gatewayValue);
            System.out.println("Success: Payment Gateway changed via Standard Select.");
        } catch (Exception e) {
            System.out.println("Standard select intercepted. Forcing selection change via JavaScript...");

            // محاولة بديلة وقاطعة: تغيير القيمة وتشغيل حدث التحديث (onchange) مباشرة بالـ JS لتخطي الحجب
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = arguments[1];", dropdownElement, gatewayValue);
            js.executeScript("arguments[0].dispatchEvent(new Event('change'));", dropdownElement);

            System.out.println("Success: Payment Gateway forced via JavaScript.");
        }

        // انتظر ثانية واحدة لتستقر الصفحة بعد تحديث بيانات الدفع
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    /**
     * الضغط على زر Make Payment لإتمام العملية
     */
    public void clickMakePayment() {
        System.out.println("Attempting to click on 'Make Payment' button...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // نضمن أيضاً اختفاء اللودر قبل الضغط على زر الدفع النهائي
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pageLoader));

            WebElement paymentBtn = wait.until(ExpectedConditions.elementToBeClickable(makePaymentButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", paymentBtn);

            paymentBtn.click();
            System.out.println("Success: 'Make Payment' button clicked!");
        } catch (Exception e) {
            System.out.println("Standard click failed. Forcing 'Make Payment' via JavaScript...");
            jsClick(makePaymentButton);
        }
    }
}