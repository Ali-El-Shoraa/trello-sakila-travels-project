package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class HomePage extends BasePage {
    //WebDriver driver;
    //created constructor with parameter that comes as object from WebDriver
    public HomePage(WebDriver driver) {
        super(driver);   //this.driver=driver;    //driver transferred from java test to jave main COZ testNG just in test file
    }
    //Locator
    private final By loginButton =By.cssSelector(".hidden.lg\\:inline-flex.btn");   //using "//" to escape in java and "/" in css
    //private final By loginButton = By.xpath("//a[@href='/login']");
    private final By warningButton = By.id("acknowledgeDemoWarning");
    private final By warningModal = By.id("demoWarningModal");
    private final By popupButton = By.xpath("//button[contains(text(),'I Understand & Continue')]");

    //Action
   /* public LoginPage clickLoginButton(){
        // handle popup لو ظهر
        try {
            waitForClickability(popupButton);
            jsClick(popupButton);
        } catch (Exception e) {
            // مفيش popup — كمّل
        }

        waitForInVisibility(By.id("page-loader"));
        waitForClickability(warningButton);  //click button to close popup (Important Notice: Demo Environment)
        jsClick(warningButton);
        waitForInVisibility(warningModal);    // wait because close layout in website
        waitForClickability(loginButton);
        driver.findElement(loginButton).click();
        return new LoginPage(driver);  //return constructor of the page that we go to
    }

    */
    public LoginPage clickLoginButton() {
        // handle popup لو ظهر — بأي شكل
        try {
            waitForClickability(popupButton);
            jsClick(popupButton);
        } catch (Exception e) {
            // مفيش popup — كمّل
        }

        try {
            waitForClickability(warningButton);
            jsClick(warningButton);
            waitForInVisibility(warningModal);
        } catch (Exception e) {
            // مفيش warning — كمّل
        }

        waitForInVisibility(By.id("page-loader"));
        waitForVisibility(loginButton);
        jsClick(loginButton);
        return new LoginPage(driver);
    }
}
