package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class FlightBookingPage extends BasePage {
    public FlightBookingPage(WebDriver driver) {
        super(driver);

    }
    //locators
      private By payLatercheck =       By.xpath("(//div[@class='flex items-center flex-1'])[1]");
      private By agreePointer=          By.xpath("//input[@id='terms_accepted']");
      private By confirmBookingButton =By.xpath("//button[@type='submit']");
      private By nationality          =By.xpath("//select[@x-model ='formData.passengers.adult_0.nationality']");
      private By passportIDNumber =By.xpath("//input[@x-model='formData.passengers.adult_0.passport_number']");//   //input[@placeholder='6 - 15 Numbers']
     // private By EgyptSelect      =By.xpath("//select[@x-model='formData.passengers.adult_0.nationality']//option[text()='Egypt']") ;     //         By.xpath("(//span[@class='material-symbols-outlined text-white text-xs checkbox-icon'])[2]");

    //actions


    public void choosPayLater(){
    //driver.findElement(payLatercheck).click();
    jsClick(payLatercheck);
}
public void chooseAgreePointer(){
   // driver.findElement(agreePointer).click();
    jsClick( agreePointer);
}

public void EnterNationality(){
     waitForVisibility(nationality);    //driver.findElement(nationality).click();make problem
                                        //driver.findElement(EgyptSelect).click();not work
    Select nationalityDropdown = new Select(driver.findElement(nationality));   //to select country after other ways not work
    nationalityDropdown.selectByVisibleText("Egypt");//to choose from menu
    //driver.findElement(nationality).sendKeys("Egypt\n");not work
}

public void EnterPassportIDNumber(String passport){
    driver.findElement(passportIDNumber).sendKeys(passport);
}

public InvoicesPage ClickONConfirmBookingButton(){
    driver.findElement(confirmBookingButton).click();
    return new InvoicesPage(driver);
}


}
