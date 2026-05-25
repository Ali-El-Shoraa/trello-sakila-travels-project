package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    //locators
    private final By emailAddressBox=By.xpath("//input[@id='email']");
    private final By passWordBox=By.id("password");
    private final By signInBox=By.xpath("//button[@type='submit']");

    //action
    public void insertEmailAddress(String emailAddress){
        waitForVisibility(emailAddressBox);
        driver.findElement(emailAddressBox).sendKeys(emailAddress);
    }
    public void insertPassword(String passWord){
        driver.findElement(passWordBox).sendKeys(passWord);
    }
    public DashboardPage clickOnSignIn(){
        waitForVisibility(signInBox);

        //driver.findElement(signInBox).click();   //didn't work so we use the following 3 lines javascript click to click direct from DOM
        jsClick(signInBox);
        return new  DashboardPage(driver);
    }

}
