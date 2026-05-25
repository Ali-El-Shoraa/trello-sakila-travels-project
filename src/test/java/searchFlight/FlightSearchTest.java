package searchFlight;
import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class FlightSearchTest extends BaseTests {

    @Test

    public void FlightTestCases() {
        LoginPage loginPage=homePage.clickLoginButton();
        loginPage.insertEmailAddress("user@phptravels.com");
        loginPage.insertPassword("demouser");
        DashboardPage dashboardPage= loginPage.clickOnSignIn();
        FlightPage flightPage = dashboardPage.clickOnFlight();

        flightPage.enterDepartureCityAirport("CAI");//cairo
        flightPage.enterArrivalCityAirport("JED");  //Jedda
        flightPage.searchOnFlight();
        //flightPage.clickOnBookNowButton();
        FlightBookingPage flightBookingPage=flightPage.clickOnBookNowButton();

        flightBookingPage.EnterNationality();
        flightBookingPage .EnterPassportIDNumber("1234567");
        flightBookingPage.choosPayLater();
        flightBookingPage.chooseAgreePointer();
        InvoicesPage invoicesPage=flightBookingPage.ClickONConfirmBookingButton();
        //assertion
        String actualResult=invoicesPage.getSuccessMessage();
        String expectedResult="Customer Information";
        Assert.assertTrue(actualResult.contains(expectedResult));

    }


}
