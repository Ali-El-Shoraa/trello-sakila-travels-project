package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    WebDriver driver;
    public DashboardPage(WebDriver driver) {
        this.driver=driver;
    }
    //locators
    //private final By dashboardWord= By.cssSelector(".font-bold.text-2xl.text-gray-900");   //not working!
     private final By dashboardWord=By.xpath("//h1[contains(text(),'Dashboard')]");

    //Actions

    public String getDashboardWord(){
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardWord));

        return driver.findElement(dashboardWord).getText();

    }


}
