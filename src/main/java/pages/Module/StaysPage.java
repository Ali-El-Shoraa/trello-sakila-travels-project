package pages.Module;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class StaysPage extends BasePage {

    // ================== LOCATORS (FIELDS) ==================
    // Destination
    private final By innerDestinationInput = By.xpath("//input[@x-ref='destinationInput']");
    private final By firstCityResult = By.xpath("(//span[text()='City']/ancestor::div[@class='flex items-center gap-2'])[1]");

    // Dates & Calendar
    private final By datepickerOverlay = By.className("datepicker-overlay");

    // Nationality
    private final By nationalityDropdownBtn = By.xpath("//span[text()='Select Nationality']");
    private final By nationalitySearchInput = By.xpath("//input[@x-ref='nationalitySearch']");

    // Search Button
    private final By searchBtn = By.xpath("//button[contains(.,'Search')]");


    // ================== CONSTRUCTOR ==================
    public StaysPage(WebDriver driver) {
        // استدعاء المشيد من BasePage لتجهيز الـ driver والـ wait والـ js تلقائياً
        super(driver);
    }


    // ================== ACTIONS (METHODS) ==================

    public void insertDestination(String city) {
        // تم استبدال التعريف المحلى بـ wait المورث من BasePage لزيادة الأمان والثبات
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(innerDestinationInput));
        input.clear();
        input.sendKeys(city);

        // Wait for the first city result to appear and click on it.
        WebElement firstCity = wait.until(ExpectedConditions.elementToBeClickable(firstCityResult));
        firstCity.click();
    }

    // Integrated dynamic function for handling overlapping Calendar windows
    public void selectCalendarDate(String inputType, String fullDate) {
        // Analysis: "25-Dec-2026"
        String[] dateParts = fullDate.split("-");
        String targetDay = dateParts[0];
        String targetMonth = dateParts[1];
        String targetYear = dateParts[2];

        // 1. انتظر حتى يختفي أي لودر أو تأثير للنافذة السابقة
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // 2. فتح نافذة التقويم المحددة (Check-in أو Check-out)
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.name(inputType)));
        dateInput.click();

        // تجهيز متغيرات الـ XPath بناءً على نوع الإدخال لضمان عدم تأثر الـ Check-in
        String baseCalendarXpath;
        if (inputType.equals("checkout_date")) {
            // للـ Check-out: نركز فقط على حاوية التقويم النشطة والظاهرة (التي لا تحتوي على كلاس hidden)
            baseCalendarXpath = "//div[contains(@class, 'datepicker') and not(contains(@class, 'hidden'))]";
        } else {
            // للـ Check-in: نتركها عائمة كالمعتاد
            baseCalendarXpath = "";
        }

        // 3. الضغط على الـ switch الخاص بواجهة الأيام
        By daysSwitch = By.xpath(baseCalendarXpath + "//div[not(contains(@class, 'datepicker-months')) and not(contains(@class, 'datepicker-years'))]//th[contains(@class, 'switch')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(daysSwitch));
        wait.until(ExpectedConditions.elementToBeClickable(daysSwitch)).click();

        // 4. الضغط على الـ switch الخاص بواجهة الشهور
        By monthsSwitch = By.xpath(baseCalendarXpath + "//div[contains(@class, 'datepicker-months')]//th[contains(@class, 'switch')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(monthsSwitch));
        wait.until(ExpectedConditions.elementToBeClickable(monthsSwitch)).click();

        // 5. اختيار السنة المطلوبة
        By yearLocator = By.xpath(baseCalendarXpath + "//span[contains(@class, 'year') and text()='" + targetYear + "']");
        wait.until(ExpectedConditions.elementToBeClickable(yearLocator)).click();

        // 6. اختيار الشهر المطلوب
        By monthLocator = By.xpath(baseCalendarXpath + "//div[contains(@class, 'datepicker-months')]//span[contains(@class, 'month') and text()='" + targetMonth + "']");
        wait.until(ExpectedConditions.elementToBeClickable(monthLocator)).click();

        // 7. اختيار اليوم المطلوب
        By dayLocator = By.xpath(baseCalendarXpath + "//div[contains(@class, 'datepicker-days')]//div[contains(@class, 'day') and not(contains(@class, 'old')) and not(contains(@class, 'new')) and text()='" + targetDay + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();

        System.out.println("Success: Date " + fullDate + " selected successfully for " + inputType);

        // Close window
        try {
            driver.findElement(By.tagName("body")).click();
        } catch (Exception e) { /* تجاهل في حال أُغلقت تلقائياً */ }
    }

    public void selectNationality(@NonNull String country) {
        // 1. Click on the external menu to open the citizenship options.
        wait.until(ExpectedConditions.elementToBeClickable(nationalityDropdownBtn)).click();

        // 2. Wait until the internal search field becomes fully visible.
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(nationalitySearchInput));

        // Click inside the field and type the country name letter by letter to select "Settlement".
        searchField.click();

        for (char ch : country.toCharArray()) {
            searchField.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("The country name was successfully written: " + country);

        // 3. Wait for the filtered country option to appear and click on it
        By countryOptionLocator = By.xpath("//div[contains(@class, 'input-dropdown-item') and .//span[text()='" + country + "']]");
        WebElement countryItem = wait.until(ExpectedConditions.elementToBeClickable(countryOptionLocator));
        countryItem.click();

        System.out.println("The country was successfully selected and the list is closed: " + country);
    }

    public void clickSearch() {
        // Wait until the transparent layer disappears.
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(datepickerOverlay));
        } catch (Exception e) {
            // تجاهل الخطأ في حال لم تكن الطبقة موجودة
        }

        // Now, press the search button safely after the screen is completely clear
        WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        searchButtonElement.click();
    }
}