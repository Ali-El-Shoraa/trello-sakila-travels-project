package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Module.StaysPage;

import java.time.Duration;

public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) {
        super(driver);
        waitForLoaderToDisappear();
    }
    //locators
     private final By dashboardWord=By.xpath("//h1[contains(text(),'Dashboard')]");
     private final By servicesMenu=By.xpath("//span[contains(text(),'Services')]");
     private final By carsBookingLink=By.xpath("//a[contains(text(),'Cars Booking')]");
     private final By toursBookingLink=By.xpath("//a[contains(text(),'Tours Booking')]");
     private final By flightsBooking =By.xpath("  //a[contains(., 'Flights')]");



    //Actions
    public String getDashboardWord(){
        waitForVisibility(dashboardWord);
        return driver.findElement(dashboardWord).getText();

    }

    public CarsPage openCarsBookingFromServicesMenu() {
        driver.findElement(servicesMenu).click();
       // hover(servicesMenu);        //   make dropdown menu appears to be clickable
        jsClick(carsBookingLink);   //   click on cars
        return new CarsPage(driver);
    }

    public ToursPage openToursBookingFromServicesMenu(){
       driver.findElement(servicesMenu).click();
        //hover(servicesMenu);
        jsClick(toursBookingLink);
        return new  ToursPage(driver);
    }

    public FlightPage clickOnFlight(){

        driver.findElement(servicesMenu).click();
        waitForVisibility(flightsBooking);
        WebElement Flights = driver.findElement(flightsBooking);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", Flights);
        // driver.findElement(flight).click();not work
        return new FlightPage(driver);
    }


    public StaysPage openStaysBooking() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. انتظر قائمة Services الرئيسية حتى تكون قابلة للضغط ثم اضغط عليها لفتح القائمة المنسدلة
        By servicesMenu = By.xpath("//span[contains(text(),'Services')]");
        wait.until(ExpectedConditions.elementToBeClickable(servicesMenu)).click();

        // 2. الـ XPath الدقيق بناءً على الـ HTML الذي أرسلته (نبحث عن الرابط الذي يذهب إلى صفحة stays)
        By staysLinkXpath = By.xpath("//a[@href='https://phptravels.net/stays']");

        // 3. ننتظر حتى تصبح القائمة قد فتحت تماماً والرابط مرئي للعين
        WebElement staysLink = wait.until(ExpectedConditions.visibilityOfElementLocated(staysLinkXpath));

        // 4. نضغط بجافا الصافية
        staysLink.click();

        return new StaysPage(driver);
    }
}
