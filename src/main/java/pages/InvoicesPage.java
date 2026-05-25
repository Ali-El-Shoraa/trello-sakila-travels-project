package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InvoicesPage extends BasePage{
    public InvoicesPage(WebDriver driver) {super(driver);
    }
    //locators

    By customerInformation =By.xpath("//h4[contains(text(),'Customer Information')]");

    //action
    public String getSuccessMessage(){
        waitForVisibility(customerInformation);
        String message=  driver.findElement(customerInformation).getText();
        return  message;}
}
