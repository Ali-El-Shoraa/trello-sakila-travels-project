package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarsPage extends BasePage {
    public CarsPage(WebDriver driver) {
        super(driver);
        waitForLoaderToDisappear();
    }
    //Locators
    private final By pickupLocation = By.cssSelector("input[x-ref='pickupInput']");
    private final By returnLocation = By.cssSelector("input[x-ref='dropoffInput']");
    private final By pickupSelection =  By.xpath("//div[contains(text(),'Borg El Arab')]");
    private final By returnSelection = By.xpath("//div[contains(text(),'Cairo International Airport')]");
    private final By submitSearchButton=By.xpath("//span[contains(text(),'Search Cars')]");


    //Actions
    public void insertPickupLocation(String location) {
        waitForClickability(pickupLocation);
        driver.findElement(pickupLocation).click();
        driver.findElement(pickupLocation).sendKeys(location);
    }
    public void insertReturnLocation(String location) {
        waitForClickability(returnLocation);
        driver.findElement(returnLocation).click();
        driver.findElement(returnLocation).sendKeys(location);
    }

    public void waitForOptionsToLoad() {
        waitForVisibility(pickupLocation);
        waitForVisibility(returnSelection);
    }

    public void selectOptions(){
        jsClick(pickupSelection);
        jsClick(returnSelection);

    }
    public RentCars clickSubmitSearchButton(){
        driver.findElement(submitSearchButton).click();
        return new RentCars(driver);
    }
}
