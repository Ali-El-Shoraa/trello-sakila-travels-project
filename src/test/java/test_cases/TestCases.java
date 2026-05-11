package test_cases;

import base.BaseTests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;

public class TestCases extends BaseTests {
    @Test
    public void testValidCredentials(){

        LoginPage loginPage=homePage.clickLoginButton();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));   //wait to fill emailAddressBox
        /*for (String windowHandle : driver.getWindowHandles()) {        //window switch after click to redirect window context
            driver.switchTo().window(windowHandle);
        }*/
        loginPage.insertEmailAddress("user@phptravels.com");
        loginPage.insertPassword("demouser");
        DashboardPage dashboardPage= loginPage.clickOnSignIn();

        //assertion
        String actualResult=dashboardPage.getDashboardWord();
        String expectedResult="Dashboard";
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
