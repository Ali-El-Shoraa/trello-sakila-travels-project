package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RentCars extends BasePage{
    public RentCars(WebDriver driver) {
        super(driver);
    }
    // check box locator after click on book now (Website doesn't open new page only redirect)
    private final By agreeTermsAncConditions = By.xpath("(//div[contains(@class,'checkbox-custom')])[2]");
    private final By confirmBookingButton = By.xpath("//span[contains(text(),'Confirm Booking')]");

    //Dynamic locate by car name and click the book now button
    public void clickBookNowForCar(String carName){
        String xpath = "//h3[contains(.,'" + carName + "')]" +
                "/ancestor::div[contains(@class,'border')]" +
                "//button[contains(.,'Book Now')]";
        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        bookButton.click();
        wait.until(ExpectedConditions.urlContains("booking"));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(agreeTermsAncConditions));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);
        realClick(checkbox);
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmBookingButton));
        realClick(confirmButton);
    }
}
