package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlightPage extends BasePage  {

    WebDriverWait wait;

    public FlightPage(WebDriver driver) {
        super(driver);
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));}
        //Locators
       By departureCity = By.xpath(" //input[@x-ref='fromInput']");
       By arrivalCity =   By.cssSelector("#arrival_airport_input ");
       By searchFlight =  By.xpath("//button[@class='btn w-full']");
       By  searchResultFlight = By.xpath("//div[@class='text-sm']");
       By bookNowButton=By.xpath("(//div[contains(@class,'items-end')]//button[contains(.,'Book Now')])[1]");
    // By.xpath("(//button[contains(., 'Book Now')])[1]");//not work ///By  bookNowButton      =By.xpath("(//span[contains(text(),'Book Now')[1])");
    //  By.cssSelector("button.btn.flex.items-center span:nth-child(2)"); make error


    //actions
    public void enterDepartureCityAirport(String departureCityAirport) {

       wait.until(ExpectedConditions.visibilityOfElementLocated(departureCity));
       //waitForClickability(departureCity);
        driver.findElement(departureCity).sendKeys(departureCityAirport);

    }
    public void enterArrivalCityAirport(String arrivalCityAirport) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(arrivalCity));
        driver.findElement(arrivalCity).sendKeys(arrivalCityAirport);
    }

public void searchOnFlight(){
      waitForClickability(searchFlight);
      jsClick(searchFlight);//add to avoid button class="btn w-full" type="submit"> is not clickable
   // driver.findElement(searchFlight).click();


    }
    public FlightBookingPage clickOnBookNowButton() {
        waitForLoaderToDisappear();
        waitForVisibility(searchResultFlight);
        //  waitForClickability(bookNowButton);
        //  driver.findElement(bookNowButton).click();
        waitForVisibility(bookNowButton);
        jsClick(bookNowButton);
        return new FlightBookingPage(driver);


    }
}
