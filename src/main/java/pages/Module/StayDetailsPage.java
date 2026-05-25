package pages.Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;

public class StayDetailsPage extends BasePage {

    // ================== VARIABLES & DRIVERS ==================
    private final WebDriverWait wait;

    // ================== LOCATORS (FIELDS) ==================
    private final By allQtyButtons = By.xpath("//table//tbody//tr//td[last()]//button");

    // 🎯 تحديث محدد الزر: الاعتماد الكلي على دالة الأكشن الفريدة bookNow() لتجنب تشويش النصوص الداخلية
//    private final By continueBookingBtn = By.xpath("//button[@click='bookNow()' or contains(@click, 'bookNow')]");

    // 🎯 الحل الجذري: البحث عن أي زر يحتوي على خاصية قيمتها bookNow() متخطين مشكلة رمز الـ @
    private final By continueBookingBtn = By.xpath("//button[contains(@*, 'bookNow()')]");

    // ================== CONSTRUCTOR ==================
    public StayDetailsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }


    // ================== ACTIONS (METHODS) ==================

    public void waitForPageToLoad() {
        System.out.println("Waiting for rooms table to be fully visible...");
        wait.until(ExpectedConditions.presenceOfElementLocated(allQtyButtons));
        System.out.println("Success: Rooms table loaded completely!");
    }

    public void clickSelectRoomByIndex(int index) {
        wait.until(ExpectedConditions.presenceOfElementLocated(allQtyButtons));
        List<WebElement> buttons = driver.findElements(allQtyButtons);

        if (index < buttons.size()) {
            WebElement targetButton = buttons.get(index);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", targetButton);

            // انتظار قصير للتأكد من ثبات العنصر بعد الـ Scroll
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

            wait.until(ExpectedConditions.elementToBeClickable(targetButton));
            targetButton.click();
            System.out.println("Success: Clicked on room option at index: " + index);
        } else {
            throw new IndexOutOfBoundsException("Error: Requested room index " + index + " but only found " + buttons.size() + " options.");
        }
    }

    /**
     * دالة للضغط على زر Continue Booking الماثل في الشريط السفلي الديناميكي
     */
    public void clickContinueBooking() {
        System.out.println("Waiting for 'Continue Booking' button to become visible and interactive...");

        // 1. الانتظار الصريح حتى يظهر الشريط السفلي والزر تماماً بعد نقرة الغرفة
        WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(continueBookingBtn));

        // 2. نزول خفيف لمركز الشاشة لضمان عدم تداخله مع أي إطارات أخرى
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", continueBtn);

        try {
            // 3. محاولة النقر العادية المستقرة للسيلينيوم
            wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
            continueBtn.click();
            System.out.println("Success: Clicked on 'Continue Booking' button via standard click.");
        } catch (Exception e) {
            System.out.println("Standard click caught an interaction block. Forcing click via JavaScript...");
            // 4. نقرة جافا سكريبت القاطعة إذا واجه السيلينيوم أي حجب رسومي مؤقت
            js.executeScript("arguments[0].click();", continueBtn);
            System.out.println("Success: Clicked on 'Continue Booking' button via JavaScript.");
        }
    }
}