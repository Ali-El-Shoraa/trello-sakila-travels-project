package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;
    //created constructor with parameter that comes as object from WebDriver
    public HomePage(WebDriver driver) {
        this.driver=driver;    //driver transferred from java test to jave main COZ testNG just in test file

    }
    //Locator
    private final By loginButton =By.cssSelector(".hidden.lg\\:inline-flex.btn");   //using "//" to escape in java and "/" in css


    //Action

    public LoginPage clickLoginButton(){
        driver.findElement(loginButton).click();
        return new LoginPage(driver);  //return constructor of the page that we go to

    }



}
