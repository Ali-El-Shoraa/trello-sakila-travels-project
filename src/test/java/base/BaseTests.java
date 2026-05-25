package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;

public class BaseTests {
    public WebDriver driver;      //same driver for all methods
    public HomePage homePage;//take object from class to define constructor
    public String validEmail = "user@phptravels.com";
    public String validPassword = "demouser";
    @BeforeMethod
    public void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://phptravels.net/");
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void closeBrowser() {

        if (driver != null) driver.quit();
    }

}
