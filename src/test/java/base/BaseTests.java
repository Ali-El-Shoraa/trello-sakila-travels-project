package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;

public class BaseTests {
    public WebDriver driver;      //same driver for all methods
    public HomePage homePage;     //take object from class to define constructor
    @BeforeClass        //open browser to start
    public void setup(){
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        homePage=new HomePage(driver);    //define constructor "driver"   that created in HomePage
    }

    @BeforeMethod      //goHome //to start every test cases from scratch
    public void goHome(){
        driver.get("https://phptravels.net/");
    }

   /* @AfterClass      //close browser after end of all cases
    public void closeBrowser(){
        driver.quit();
    }*/


}
