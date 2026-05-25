package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ToursPage extends BasePage {
    public ToursPage(WebDriver driver) {
        super(driver);
        waitForLoaderToDisappear();
    }
    //locators
    private final By destinationBox = By.xpath("(//input[@type='text'])[1]");
    private final By startDateBox = By.xpath("(//input[@type='text'])[2]");
    private final By destinationSelection =  By.xpath("//span[contains(text(),'Dubai')]");
    // private final By startDateSelection =  By.xpath("//div[contains(@class, 'day') and contains(@class, 'bg-blue-600') and text()='29']");//  select number and state
    private final By startDateSelection=By.xpath("//input[@name='start_date']");
    private final By ToursSearchButton=By.xpath("//button[@type='submit']");

    //action
    public void insertDestination(String location){
        waitForVisibility(destinationBox);
        driver.findElement(destinationBox).click();
        driver.findElement(destinationBox).sendKeys(location);
    }

    public void waitOptionsToLoad(){
        waitForVisibility(destinationSelection);
        waitForVisibility(startDateSelection);
    }
    public void selectOptions(){
        jsClick(destinationSelection);
        jsClick(startDateSelection);
    }
    public BookTourPage clickOnToursSearchButton(){
        //driver.findElement(ToursSearchButton);
        jsClick(ToursSearchButton);
        return new BookTourPage(driver);
    }
}
