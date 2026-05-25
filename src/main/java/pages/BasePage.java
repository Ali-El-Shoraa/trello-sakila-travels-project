package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInVisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForClickability(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].click();", element);
    }

    public void waitForLoaderToDisappear() {
        waitForInVisibility(By.id("page-loader"));
    }

    public void realClick(WebElement element){
        new Actions(driver)
                .moveToElement(element)
                .pause(Duration.ofMillis(500))
                .click()
                .perform();
    }


    public void hover(By locator) {
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element).perform();
    }
    // Function to Scroll to end of page
    public void scrollToBottom(By servicesMenu) {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    // function to make selenium focus on new window if the button doesn't direct to new page
    public void switchToNewWindow() {
        String currentWindow = driver.getWindowHandle();

        for(String window : driver.getWindowHandles()){
            if(!window.equals(currentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
    }
}