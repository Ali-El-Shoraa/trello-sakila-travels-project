package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookTourPage extends BasePage {
    public BookTourPage(WebDriver driver) {
        super(driver);
    }

    //locators
    private final By bookNowButtonForTours = By.xpath("//a[.//span[text()='Book Now']]");
    private final By termsCheckboxTours = By.id("terms_accepted");
    private final By confirmBookingButtonTours = By.cssSelector("button[type='submit']");

    //actions
    public void clickMoreDetailsByTourName(String tourName) {  //this xpath sends the tourName then gose to button that belong.
        String moreDetailsDynamicXpath = "//h3[contains(text(),'" + tourName + "')]" +
                "/ancestor::div[contains(@class,'card')]" +
                "//a[contains(.,'More Details')]";

        waitForClickability(By.xpath(moreDetailsDynamicXpath));
        driver.findElement(By.xpath(moreDetailsDynamicXpath)).click();


        waitForVisibility(bookNowButtonForTours);
        jsClick(bookNowButtonForTours);

        waitForClickability(termsCheckboxTours);
        WebElement checkBox=driver.findElement(termsCheckboxTours);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",checkBox);
        new Actions(driver)                //action >>press on html not Alpine.js that controlled by checkbox
                .moveToElement(checkBox)
                .pause(Duration.ofMillis(500))
                .click()
                .perform();
        jsClick(termsCheckboxTours);

        WebElement confirmButton = driver.findElement(confirmBookingButtonTours);
        waitForClickability(confirmBookingButtonTours);
        new Actions(driver)
                .moveToElement(confirmButton)
                .pause(Duration.ofMillis(500))
                .click()
                .perform();

    }
}
