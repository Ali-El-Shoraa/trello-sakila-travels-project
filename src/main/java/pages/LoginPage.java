package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver=driver;
    }
    //locators
    private final By emailAddressBox=By.xpath("//input[@id='email']");
    private final By passWordBox=By.id("password");
    private final By signInBox=By.xpath("//button[@type='submit']");

    //action
    public void insertEmailAddress(String emailAddress){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailAddressBox));

        driver.findElement(emailAddressBox).sendKeys(emailAddress);
    }
    public void insertPassword(String passWord){
        driver.findElement(passWordBox).sendKeys(passWord);
    }
    public DashboardPage clickOnSignIn(){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInBox));

        //driver.findElement(signInBox).click();   //didn't work so we use the following 3 lines javascript click to click direct from DOM
        WebElement signInButton = driver.findElement(signInBox);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", signInButton);


        return new  DashboardPage(driver);
    }

}
