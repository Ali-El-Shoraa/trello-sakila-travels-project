package test_cases;
import base.BaseTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import pages.Module.*;

public class TestCases extends BaseTests {
    @Test (priority = 2)
    public void rentCarModel() {
        // Every Test Case --------Should Start With-----//
        //Test Valid Credentials and log in dashboard
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.insertEmailAddress(validEmail);
        loginPage.insertPassword(validPassword);
        DashboardPage dashboardPage = loginPage.clickOnSignIn();
        //assertion
        String actualResult = dashboardPage.getDashboardWord();
        String expectedResult = "Dashboard";
        Assert.assertTrue(actualResult.contains(expectedResult));
        BasePage basePage = new BasePage(driver);
        basePage.waitForLoaderToDisappear();
        //-----------------------------------------------------------------------//
        // Booking Car Scenario
        CarsPage carsPage =dashboardPage.openCarsBookingFromServicesMenu();
        carsPage.waitForLoaderToDisappear();
        carsPage.insertPickupLocation("Borg El Arab");
        carsPage.insertReturnLocation("Cairo International");
        carsPage.waitForOptionsToLoad();
        carsPage.selectOptions();
        RentCars rentCars =carsPage.clickSubmitSearchButton();
        rentCars.clickBookNowForCar("Mercedes");

    }
    //-----------------------------------------------------------------------//
    @Test (priority = 1)
    public void tourModel(){
        // Every Test Case --------Should Start With-----//
        //Test Valid Credentials and log in dashboard
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.insertEmailAddress(validEmail);
        loginPage.insertPassword(validPassword);
        DashboardPage dashboardPage = loginPage.clickOnSignIn();

        //assertion
        String actualResult = dashboardPage.getDashboardWord();
        String expectedResult = "Dashboard";
        Assert.assertTrue(actualResult.contains(expectedResult));
        BasePage basePage = new BasePage(driver);
        basePage.waitForLoaderToDisappear();
        //-----------------------------------------------------------------------//
        // Booking tour Scenario

        ToursPage toursPage = dashboardPage.openToursBookingFromServicesMenu();
        toursPage.insertDestination("Dubai");
        //toursPage.insertStartDate("June 2026","29");
        toursPage.waitOptionsToLoad();
        toursPage.selectOptions();
        toursPage.clickOnToursSearchButton();

        BookTourPage bookTourPage= new BookTourPage(driver);
        bookTourPage.clickMoreDetailsByTourName("Dubai Private Transfer: Cruise Port to Dubai Hotel");
    }

    @Test(priority = 3)
    public void selectStaysLink() {
        // Every Test Case --------Should Start With-----//
        String validEmail = "user@phptravels.com";
        String validPassword = "demouser";
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.insertEmailAddress(validEmail);
        loginPage.insertPassword(validPassword);
        DashboardPage dashboardPage = loginPage.clickOnSignIn();

        // assertion
        String actualResult = dashboardPage.getDashboardWord();
        String expectedResult = "Dashboard";
        Assert.assertTrue(actualResult.contains(expectedResult));
        BasePage basePage = new BasePage(driver);
        basePage.waitForLoaderToDisappear();

        StaysPage staysPage = dashboardPage.openStaysBooking();

        staysPage.insertDestination("Dubai");
        staysPage.selectNationality("Algeria");

        // Select entry and exit dates
//        staysPage.selectCalendarDate("checkin_date", "28-May-2026");
//        staysPage.selectCalendarDate("checkout_date", "30-Jun-2026");
        staysPage.clickSearch();

        // Go to the results page and apply the filters.
        SearchStaysResultsPage resultsPage = new SearchStaysResultsPage(driver);
        resultsPage.filterByAccommodationType("Hotel");
        resultsPage.filterByPriceRange(10, -10);

        // Choose the order from highest to lowest price
        resultsPage.selectSortByOption("price_high");
        resultsPage.searchByHotelNameFromSidebar("Palazzo Versace Dubai");//Palazzo Versace Dubai || Marriott Executive Apartments Dubai Creek

        // 1. First, check the number of hotels shown.
        int hotelsCount = resultsPage.getDisplayedHotelsCount();
        System.out.println("Total hotels found: " + hotelsCount);

        if (hotelsCount > 0) {
            // 2. Click on the selected hotel to go to the details page.
            boolean isFound = resultsPage.selectHotelByName("Palazzo Versace Dubai");
            if (!isFound) {
                Assert.fail("The requested hotel is not among the available results!");
            }

            // 3. Create a details page object and wait for the rooms and prices to load.
            StayDetailsPage stayDetailsPage = new StayDetailsPage(driver);
            stayDetailsPage.waitForPageToLoad();

            // 4. Select the first room from the list (Index 0)
            stayDetailsPage.clickSelectRoomByIndex(0);

            // 5. Click the Continue Booking button
            stayDetailsPage.clickContinueBooking();

            // ========================================================
            // Payment step: Choose your payment method and confirm your booking.
            // ========================================================
            pages.Module.StayPaymentPage paymentPage = new pages.Module.StayPaymentPage(driver);

            // Choosing the initial payment method
            paymentPage.selectPaymentMethod("Pay Later");

            // Enhanced and stable pressure on the conditions and confirmation button
            paymentPage.acceptTermsAndConfirmBooking();
            // ========================================================

            // ========================================================
            // The invoice step and choosing a payment gateway (Stays Invoice)
            // ========================================================
            System.out.println("Redirecting to Invoice Page...");
            StayInvoicePage invoicePage = new StayInvoicePage(driver);

            // Change the payment gateway to Credit Card (Stripe)
            invoicePage.selectPaymentGateway("7");

            // الضغط على Make Payment
            invoicePage.clickMakePayment();
            // ========================================================

            // ========================================================
            // Proceed to Payment (confirm the transition to the payment gateway)
            // ========================================================
            System.out.println("Redirecting to Proceed Payment Page...");
            StayProceedPaymentPage proceedPage = new StayProceedPaymentPage(driver);

            // Click the Proceed to Payment button to go to the Stripe website
            proceedPage.clickProceedToPayment();
            // ========================================================

            // ========================================================
            // Step to deal with the external Stripe gateway (Stripe Checkout)
            // ========================================================
            StripeCheckoutPage stripePage = new StripeCheckoutPage(driver);

            // Click on Pay without Link within Stripe
            stripePage.clickPayWithoutLink();
            // ========================================================

            // 2. Enter the approved trial payment card details and press the payment button.
            stripePage.fillCardInformationAndSubmit(
                    "4242424242424242", // Stripe Test Card
                    "1229",             // Month and year
                    "123",              // CVC security code
                    "Test Automation"   // Cardholder's name
            );

        } else {
            System.out.println("(No hotels available)");
        }
    }

}
